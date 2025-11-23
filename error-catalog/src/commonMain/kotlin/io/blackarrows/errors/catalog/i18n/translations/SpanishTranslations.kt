package io.blackarrows.errors.catalog.i18n.translations

import io.blackarrows.errors.catalog.i18n.ErrorKeys

/**
 * Spanish (Español) translations for all error messages.
 */
internal val spanishTranslations: Map<String, String> = mapOf(
    // ============================================================================
    // Network Errors (10-XXX)
    // ============================================================================
    ErrorKeys.NETWORK_UNAVAILABLE to "La red no está disponible. Por favor, verifica tu conexión.",
    ErrorKeys.NETWORK_DNS_FAILURE to "No se puede alcanzar el servidor. Por favor, verifica tu conexión.",
    ErrorKeys.NETWORK_SSL_ERROR to "La conexión segura falló. Por favor, inténtalo de nuevo más tarde.",
    ErrorKeys.NETWORK_CONNECTION_REFUSED to "No se puede conectar al servidor. Por favor, inténtalo de nuevo más tarde.",
    ErrorKeys.NETWORK_TIMEOUT to "La solicitud ha caducado. Por favor, inténtalo de nuevo.",
    ErrorKeys.NETWORK_UNKNOWN to "Ocurrió un error de red. Por favor, inténtalo de nuevo.",

    // ============================================================================
    // Storage Errors (11-XXX)
    // ============================================================================
    ErrorKeys.STORAGE_UNAVAILABLE to "El almacenamiento no está disponible. Por favor, inténtalo de nuevo más tarde.",
    ErrorKeys.STORAGE_INSUFFICIENT_SPACE to "Espacio de almacenamiento insuficiente. Por favor, libera espacio e inténtalo de nuevo.",
    ErrorKeys.STORAGE_DATABASE_CORRUPTED to "Se detectó un error en la base de datos. Por favor, contacta con soporte.",
    ErrorKeys.STORAGE_READ_FAILED to "Error al leer los datos. Por favor, inténtalo de nuevo.",
    ErrorKeys.STORAGE_WRITE_FAILED to "Error al guardar los datos. Por favor, inténtalo de nuevo.",
    ErrorKeys.STORAGE_DELETE_FAILED to "Error al eliminar los datos. Por favor, inténtalo de nuevo.",
    ErrorKeys.STORAGE_UNKNOWN to "Ocurrió un error de almacenamiento. Por favor, inténtalo de nuevo.",

    // ============================================================================
    // Auth Errors (12-XXX)
    // ============================================================================
    ErrorKeys.AUTH_UNAUTHORIZED to "Tu sesión ha expirado. Por favor, inicia sesión de nuevo.",
    ErrorKeys.AUTH_TOKEN_EXPIRED to "Tu sesión ha expirado. Por favor, inicia sesión de nuevo.",
    ErrorKeys.AUTH_FORBIDDEN to "No tienes permiso para acceder a este recurso.",
    ErrorKeys.AUTH_REFRESH_TOKEN_INVALID to "No se puede renovar la sesión. Por favor, inicia sesión de nuevo.",
    ErrorKeys.AUTH_UNKNOWN to "Ocurrió un error de autenticación. Por favor, inténtalo de nuevo.",

    // ============================================================================
    // Session Errors - Fetch Operations (20-000 to 20-099)
    // ============================================================================
    ErrorKeys.SESSION_FETCH_NETWORK_UNAVAILABLE to "Error al obtener la sesión. Por favor, verifica tu conexión e inténtalo de nuevo.",
    ErrorKeys.SESSION_FETCH_STORAGE_ERROR to "Error al obtener la sesión debido a un error de almacenamiento. Por favor, inténtalo de nuevo.",
    ErrorKeys.SESSION_FETCH_NOT_FOUND to "Sesión no encontrada. Por favor, inicia sesión de nuevo.",
    ErrorKeys.SESSION_FETCH_INVALID_DATA to "Los datos de la sesión no son válidos. Por favor, inicia sesión de nuevo.",
    ErrorKeys.SESSION_FETCH_TIMEOUT to "La obtención de la sesión ha caducado. Por favor, inténtalo de nuevo.",
    ErrorKeys.SESSION_FETCH_UNKNOWN to "Error al obtener la sesión. Por favor, inténtalo de nuevo.",

    // ============================================================================
    // Session Errors - Login Operations (20-050 to 20-054)
    // ============================================================================
    ErrorKeys.SESSION_LOGIN_INVALID_CREDENTIALS to "Usuario o contraseña incorrectos. Por favor, inténtalo de nuevo.",
    ErrorKeys.SESSION_LOGIN_ACCOUNT_LOCKED to "Tu cuenta ha sido bloqueada. Por favor, contacta con soporte.",
    ErrorKeys.SESSION_LOGIN_EMAIL_NOT_VERIFIED to "Por favor, verifica tu dirección de correo antes de iniciar sesión.",
    ErrorKeys.SESSION_LOGIN_SERVER_ERROR to "Error de inicio de sesión debido a un error del servidor. Por favor, inténtalo de nuevo más tarde.",
    ErrorKeys.SESSION_LOGIN_NETWORK_ERROR to "Error de inicio de sesión. Por favor, verifica tu conexión e inténtalo de nuevo.",

    // ============================================================================
    // Session Errors - Save Operations (20-100 to 20-199)
    // ============================================================================
    ErrorKeys.SESSION_SAVE_STORAGE_ERROR to "Error al guardar la sesión. Por favor, inténtalo de nuevo.",
    ErrorKeys.SESSION_SAVE_VALIDATION_FAILED to "Los datos de la sesión no son válidos. Por favor, verifica tu entrada.",
    ErrorKeys.SESSION_SAVE_UNKNOWN to "Error al guardar la sesión. Por favor, inténtalo de nuevo.",
)
