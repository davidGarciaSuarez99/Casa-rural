// ── auth.js — Gestión de autenticación compartida ──
// Importar en cada HTML: <script src="auth.js"></script>

const AUTH = {
    // Obtener el token guardado
    getToken() {
        return localStorage.getItem('token');
    },

    // Comprobar si hay sesión activa
    estaLogueado() {
        return !!localStorage.getItem('token');
    },

    // Obtener el rol del usuario
    getRol() {
        return localStorage.getItem('rol');
    },

    // Comprobar si es admin
    esAdmin() {
        return localStorage.getItem('rol') === 'ADMIN';
    },


    // Borra todos los datos de sesión del localStorage.
    // Si redirigir=true, manda al usuario a la página de login.
    cerrarSesion(redirigir = true) {
        localStorage.removeItem('token');
        localStorage.removeItem('userId');
        localStorage.removeItem('nombre');
        localStorage.removeItem('apellidos');
        localStorage.removeItem('email');
        localStorage.removeItem('rol');
        if (redirigir) {
            window.location.href = 'login.html';
        }
    },

    // Protege una página: si el usuario no está logueado, lo redirige al login.
    requerirLogin() {
        if (!this.estaLogueado()) {
            window.location.href = 'login.html';
        }
    },

    // Protege una página solo para admins: si no es admin, lo manda al login.
    requerirAdmin() {
        if (!this.estaLogueado() || !this.esAdmin()) {
            window.location.href = 'login.html';
        }
    }
};

// Versión mejorada de fetch() que añade automáticamente el token JWT en las cabeceras.
// Si el servidor responde con 401 o 403 (sin permisos), cierra la sesión.
// En la página principal no redirige para no molestar a usuarios no logueados.
async function fetchConToken(url, opciones = {}) {
    const token = AUTH.getToken();

    const cabeceras = {
        'Content-Type': 'application/json',
        ...opciones.headers
    };

    if (token) {
        cabeceras['Authorization'] = `Bearer ${token}`;
    }

    try {
        const respuesta = await fetch(url, {
            ...opciones,
            headers: cabeceras
        });

        // CONTROL DE AUTORIZACIÓN: Evita echar al usuario si está en la página principal
        if (respuesta.status === 401 || respuesta.status === 403) {
            console.warn("Acceso no autorizado (401/403).");

            const path = window.location.pathname;

            if (path.includes('index.html') || path === '/' || path === '') {
                AUTH.cerrarSesion(false); // Limpia token viejo pero NO redirige
                return respuesta;
            } else {
                AUTH.cerrarSesion(true); // En pestañas protegidas sí redirige a login
                return new Promise(() => {});
            }
        }

        return respuesta;

    } catch (error) {
        console.error("Error crítico de conexión con el backend:", error);
        throw error;
    }
}