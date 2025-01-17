package com.grupo1.demo.config;

public class Views {
    // Vista NO-CRUD: Incluye datos básicos
    public static class NoCrudView {}

    // Vista CRUD: Extiende NoCrudView e incluye datos adicionales como permisos
    public static class CrudView extends NoCrudView {}
}

