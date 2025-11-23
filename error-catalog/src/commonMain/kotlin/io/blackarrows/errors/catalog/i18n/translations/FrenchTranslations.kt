package io.blackarrows.errors.catalog.i18n.translations

import io.blackarrows.errors.catalog.i18n.ErrorKeys

/**
 * French (Français) translations for all error messages.
 */
internal val frenchTranslations: Map<String, String> = mapOf(
    // ============================================================================
    // Network Errors (10-XXX)
    // ============================================================================
    ErrorKeys.NETWORK_UNAVAILABLE to "Le réseau est indisponible. Veuillez vérifier votre connexion.",
    ErrorKeys.NETWORK_DNS_FAILURE to "Impossible d'atteindre le serveur. Veuillez vérifier votre connexion.",
    ErrorKeys.NETWORK_SSL_ERROR to "La connexion sécurisée a échoué. Veuillez réessayer plus tard.",
    ErrorKeys.NETWORK_CONNECTION_REFUSED to "Impossible de se connecter au serveur. Veuillez réessayer plus tard.",
    ErrorKeys.NETWORK_TIMEOUT to "La requête a expiré. Veuillez réessayer.",
    ErrorKeys.NETWORK_UNKNOWN to "Une erreur réseau s'est produite. Veuillez réessayer.",

    // ============================================================================
    // Storage Errors (11-XXX)
    // ============================================================================
    ErrorKeys.STORAGE_UNAVAILABLE to "Le stockage est indisponible. Veuillez réessayer plus tard.",
    ErrorKeys.STORAGE_INSUFFICIENT_SPACE to "Espace de stockage insuffisant. Veuillez libérer de l'espace et réessayer.",
    ErrorKeys.STORAGE_DATABASE_CORRUPTED to "Erreur de base de données détectée. Veuillez contacter le support.",
    ErrorKeys.STORAGE_READ_FAILED to "Échec de la lecture des données. Veuillez réessayer.",
    ErrorKeys.STORAGE_WRITE_FAILED to "Échec de l'enregistrement des données. Veuillez réessayer.",
    ErrorKeys.STORAGE_DELETE_FAILED to "Échec de la suppression des données. Veuillez réessayer.",
    ErrorKeys.STORAGE_UNKNOWN to "Une erreur de stockage s'est produite. Veuillez réessayer.",

    // ============================================================================
    // Auth Errors (12-XXX)
    // ============================================================================
    ErrorKeys.AUTH_UNAUTHORIZED to "Votre session a expiré. Veuillez vous reconnecter.",
    ErrorKeys.AUTH_TOKEN_EXPIRED to "Votre session a expiré. Veuillez vous reconnecter.",
    ErrorKeys.AUTH_FORBIDDEN to "Vous n'avez pas la permission d'accéder à cette ressource.",
    ErrorKeys.AUTH_REFRESH_TOKEN_INVALID to "La session ne peut pas être renouvelée. Veuillez vous reconnecter.",
    ErrorKeys.AUTH_UNKNOWN to "Une erreur d'authentification s'est produite. Veuillez réessayer.",

    // ============================================================================
    // Session Errors - Fetch Operations (20-000 to 20-099)
    // ============================================================================
    ErrorKeys.SESSION_FETCH_NETWORK_UNAVAILABLE to "Échec de récupération de la session. Veuillez vérifier votre connexion et réessayer.",
    ErrorKeys.SESSION_FETCH_STORAGE_ERROR to "Échec de récupération de la session en raison d'une erreur de stockage. Veuillez réessayer.",
    ErrorKeys.SESSION_FETCH_NOT_FOUND to "Session introuvable. Veuillez vous reconnecter.",
    ErrorKeys.SESSION_FETCH_INVALID_DATA to "Les données de session sont invalides. Veuillez vous reconnecter.",
    ErrorKeys.SESSION_FETCH_TIMEOUT to "La récupération de la session a expiré. Veuillez réessayer.",
    ErrorKeys.SESSION_FETCH_UNKNOWN to "Échec de récupération de la session. Veuillez réessayer.",

    // ============================================================================
    // Session Errors - Login Operations (20-050 to 20-054)
    // ============================================================================
    ErrorKeys.SESSION_LOGIN_INVALID_CREDENTIALS to "Nom d'utilisateur ou mot de passe incorrect. Veuillez réessayer.",
    ErrorKeys.SESSION_LOGIN_ACCOUNT_LOCKED to "Votre compte a été verrouillé. Veuillez contacter le support.",
    ErrorKeys.SESSION_LOGIN_EMAIL_NOT_VERIFIED to "Veuillez vérifier votre adresse e-mail avant de vous connecter.",
    ErrorKeys.SESSION_LOGIN_SERVER_ERROR to "Échec de la connexion en raison d'une erreur du serveur. Veuillez réessayer plus tard.",
    ErrorKeys.SESSION_LOGIN_NETWORK_ERROR to "Échec de la connexion. Veuillez vérifier votre connexion et réessayer.",

    // ============================================================================
    // Session Errors - Save Operations (20-100 to 20-199)
    // ============================================================================
    ErrorKeys.SESSION_SAVE_STORAGE_ERROR to "Échec de l'enregistrement de la session. Veuillez réessayer.",
    ErrorKeys.SESSION_SAVE_VALIDATION_FAILED to "Les données de session sont invalides. Veuillez vérifier votre saisie.",
    ErrorKeys.SESSION_SAVE_UNKNOWN to "Échec de l'enregistrement de la session. Veuillez réessayer.",
)
