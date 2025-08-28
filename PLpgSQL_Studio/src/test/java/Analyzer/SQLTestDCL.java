package Analyzer;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import com.cunoc.drymnz.plpgsql_studio.c_interfaces.analyzer.AnalyzerSQL;

public class SQLTestDCL {

    // ====== CREATE USER Tests ======

    @Test
    void createUserSimple() {
        String textToAnalyze = """
            CREATE USER admin_ventas;""";
        AnalyzerSQL analyzer = new AnalyzerSQL(textToAnalyze);
        analyzer.analyzer();
        Assertions.assertThat(analyzer.isError()).isFalse();
    }

    @Test
    void createUserMultiple() {
        String textToAnalyze = """
            CREATE USER admin_ventas;
            CREATE USER consultor_rh;
            CREATE USER operador_sistema;""";
        AnalyzerSQL analyzer = new AnalyzerSQL(textToAnalyze);
        analyzer.analyzer();
        Assertions.assertThat(analyzer.isError()).isFalse();
    }

    @Test
    void createUserValidIdentifiers() {
        String textToAnalyze = """
            CREATE USER admin_sistema_2024;
            CREATE USER _usuario_especial;
            CREATE USER operador1;""";
        AnalyzerSQL analyzer = new AnalyzerSQL(textToAnalyze);
        analyzer.analyzer();
        Assertions.assertThat(analyzer.isError()).isFalse();
    }

    // ====== GRANT Tests ======

    @Test
    void grantSelectPermission() {
        String textToAnalyze = """
            GRANT SELECT ON ventas.clientes TO consultor_rh;""";
        AnalyzerSQL analyzer = new AnalyzerSQL(textToAnalyze);
        analyzer.analyzer();
        Assertions.assertThat(analyzer.isError()).isFalse();
    }

    @Test
    void grantInsertPermission() {
        String textToAnalyze = """
            GRANT INSERT ON ventas.productos TO admin_ventas;""";
        AnalyzerSQL analyzer = new AnalyzerSQL(textToAnalyze);
        analyzer.analyzer();
        Assertions.assertThat(analyzer.isError()).isFalse();
    }

    @Test
    void grantUpdatePermission() {
        String textToAnalyze = """
            GRANT UPDATE ON inventario.productos TO operador_sistema;""";
        AnalyzerSQL analyzer = new AnalyzerSQL(textToAnalyze);
        analyzer.analyzer();
        Assertions.assertThat(analyzer.isError()).isFalse();
    }

    @Test
    void grantDeletePermission() {
        String textToAnalyze = """
            GRANT DELETE ON recursos_humanos.empleados TO admin_ventas;""";
        AnalyzerSQL analyzer = new AnalyzerSQL(textToAnalyze);
        analyzer.analyzer();
        Assertions.assertThat(analyzer.isError()).isFalse();
    }

    @Test
    void grantAllTablesInSchema() {
        String textToAnalyze = """
            GRANT SELECT ON ventas.* TO consultor_rh;
            GRANT UPDATE ON ventas.* TO admin_ventas;""";
        AnalyzerSQL analyzer = new AnalyzerSQL(textToAnalyze);
        analyzer.analyzer();
        Assertions.assertThat(analyzer.isError()).isFalse();
    }

    @Test
    void grantMultiplePermissions() {
        String textToAnalyze = """
            GRANT SELECT ON ventas.clientes TO consultor_rh;
            GRANT INSERT ON ventas.productos TO admin_ventas;
            GRANT UPDATE ON ventas.* TO admin_ventas;
            GRANT DELETE ON recursos_humanos.empleados TO admin_ventas;""";
        AnalyzerSQL analyzer = new AnalyzerSQL(textToAnalyze);
        analyzer.analyzer();
        Assertions.assertThat(analyzer.isError()).isFalse();
    }

    // ====== REVOKE Tests ======

    @Test
    void revokeSelectPermission() {
        String textToAnalyze = """
            REVOKE SELECT ON ventas.clientes FROM consultor_rh;""";
        AnalyzerSQL analyzer = new AnalyzerSQL(textToAnalyze);
        analyzer.analyzer();
        Assertions.assertThat(analyzer.isError()).isFalse();
    }

    @Test
    void revokeInsertPermission() {
        String textToAnalyze = """
            REVOKE INSERT ON ventas.productos FROM admin_ventas;""";
        AnalyzerSQL analyzer = new AnalyzerSQL(textToAnalyze);
        analyzer.analyzer();
        Assertions.assertThat(analyzer.isError()).isFalse();
    }

    @Test
    void revokeUpdatePermission() {
        String textToAnalyze = """
            REVOKE UPDATE ON ventas.productos FROM operador_sistema;""";
        AnalyzerSQL analyzer = new AnalyzerSQL(textToAnalyze);
        analyzer.analyzer();
        Assertions.assertThat(analyzer.isError()).isFalse();
    }

    @Test
    void revokeDeletePermission() {
        String textToAnalyze = """
            REVOKE DELETE ON recursos_humanos.empleados FROM admin_ventas;""";
        AnalyzerSQL analyzer = new AnalyzerSQL(textToAnalyze);
        analyzer.analyzer();
        Assertions.assertThat(analyzer.isError()).isFalse();
    }

    @Test
    void revokeMultiplePermissions() {
        String textToAnalyze = """
            REVOKE SELECT ON ventas.clientes FROM consultor_rh;
            REVOKE INSERT ON ventas.productos FROM admin_ventas;
            REVOKE UPDATE ON inventario.productos FROM operador_sistema;""";
        AnalyzerSQL analyzer = new AnalyzerSQL(textToAnalyze);
        analyzer.analyzer();
        Assertions.assertThat(analyzer.isError()).isFalse();
    }

    @Test
    void revokeAllTablesInSchema() {
        String textToAnalyze = """
            REVOKE SELECT ON ventas.* FROM consultor_rh;
            REVOKE UPDATE ON inventario.* FROM operador_sistema;""";
        AnalyzerSQL analyzer = new AnalyzerSQL(textToAnalyze);
        analyzer.analyzer();
        Assertions.assertThat(analyzer.isError()).isFalse();
    }

    // ====== PL/pgSQL DECLARE Tests ======

    @Test
    void declareIntegerVariable() {
        String textToAnalyze = """
            DECLARE total_ventas INTEGER;""";
        AnalyzerSQL analyzer = new AnalyzerSQL(textToAnalyze);
        analyzer.analyzer();
        Assertions.assertThat(analyzer.isError()).isFalse();
    }

    @Test
    void declareVarcharVariable() {
        String textToAnalyze = """
            DECLARE nombre_cliente VARCHAR(100);""";
        AnalyzerSQL analyzer = new AnalyzerSQL(textToAnalyze);
        analyzer.analyzer();
        Assertions.assertThat(analyzer.isError()).isFalse();
    }

    @Test
    void declareBooleanVariable() {
        String textToAnalyze = """
            DECLARE es_activo BOOLEAN;""";
        AnalyzerSQL analyzer = new AnalyzerSQL(textToAnalyze);
        analyzer.analyzer();
        Assertions.assertThat(analyzer.isError()).isFalse();
    }

    @Test
    void declareIntVariable() {
        String textToAnalyze = """
            DECLARE contador INT;""";
        AnalyzerSQL analyzer = new AnalyzerSQL(textToAnalyze);
        analyzer.analyzer();
        Assertions.assertThat(analyzer.isError()).isFalse();
    }

    @Test
    void declareMultipleVariables() {
        String textToAnalyze = """
            DECLARE total_ventas INTEGER;
            DECLARE nombre_cliente VARCHAR(100);
            DECLARE es_activo BOOLEAN;""";
        AnalyzerSQL analyzer = new AnalyzerSQL(textToAnalyze);
        analyzer.analyzer();
        Assertions.assertThat(analyzer.isError()).isFalse();
    }

    @Test
    void declareVariablesAllTypes() {
        String textToAnalyze = """
            DECLARE contador INT;
            DECLARE cantidad INTEGER;
            DECLARE descripcion VARCHAR(255);
            DECLARE habilitado BOOLEAN;""";
        AnalyzerSQL analyzer = new AnalyzerSQL(textToAnalyze);
        analyzer.analyzer();
        Assertions.assertThat(analyzer.isError()).isFalse();
    }

    @Test
    void declareValidIdentifiers() {
        String textToAnalyze = """
            DECLARE _variable_privada INTEGER;
            DECLARE nombre_usuario1 VARCHAR(50);
            DECLARE es_valido BOOLEAN;""";
        AnalyzerSQL analyzer = new AnalyzerSQL(textToAnalyze);
        analyzer.analyzer();
        Assertions.assertThat(analyzer.isError()).isFalse();
    }

    // ====== Complex Combined Operations ======

    @Test
    void completeSchemaOperations() {
        String textToAnalyze = """
            CREATE SCHEMA tienda_online;
            USE SCHEMA tienda_online;
            
            CREATE TABLE categorias (
                id INTEGER PRIMARY KEY,
                nombre VARCHAR(50) NOT NULL
            );
            
            CREATE TABLE productos (
                id INTEGER PRIMARY KEY,
                nombre VARCHAR(100) NOT NULL,
                precio INTEGER,
                activo BOOLEAN,
                categoria_id INTEGER,
                FOREIGN KEY (categoria_id) REFERENCES categorias(id)
            );
            
            INSERT INTO categorias (id, nombre) VALUES (1, 'Electronica');
            INSERT INTO productos (id, nombre, precio, activo, categoria_id) 
            VALUES (1, 'Smartphone', 25000, TRUE, 1);
            
            CREATE USER vendedor;
            GRANT SELECT ON tienda_online.productos TO vendedor;
            
            SELECT p.nombre, c.nombre 
            FROM productos p JOIN categorias c ON p.categoria_id = c.id 
            WHERE p.activo = TRUE;""";
        AnalyzerSQL analyzer = new AnalyzerSQL(textToAnalyze);
        analyzer.analyzer();
        Assertions.assertThat(analyzer.isError()).isFalse();
    }

    @Test
    void multipleSchemaOperations() {
        String textToAnalyze = """
            CREATE SCHEMA ventas;
            CREATE SCHEMA inventario;
            
            USE SCHEMA ventas;
            CREATE TABLE clientes (id INTEGER PRIMARY KEY, nombre VARCHAR(100));
            
            USE SCHEMA inventario;
            CREATE TABLE productos (id INTEGER PRIMARY KEY, stock INTEGER);
            
            INSERT INTO ventas.clientes (id, nombre) VALUES (1, 'Cliente A');
            INSERT INTO inventario.productos (id, stock) VALUES (1, 100);
            
            CREATE USER consultor;
            CREATE USER administrador;
            
            GRANT SELECT ON ventas.* TO consultor;
            GRANT UPDATE ON inventario.productos TO administrador;""";
        AnalyzerSQL analyzer = new AnalyzerSQL(textToAnalyze);
        analyzer.analyzer();
        Assertions.assertThat(analyzer.isError()).isFalse();
    }

    @Test
    void fullWorkflowExample() {
        String textToAnalyze = """
            CREATE SCHEMA empresa;
            USE SCHEMA empresa;
            
            CREATE TABLE departamentos (
                id INTEGER PRIMARY KEY,
                nombre VARCHAR(100) NOT NULL
            );
            
            CREATE TABLE empleados (
                id INTEGER PRIMARY KEY,
                nombre VARCHAR(100) NOT NULL,
                activo BOOLEAN,
                departamento_id INTEGER,
                FOREIGN KEY (departamento_id) REFERENCES departamentos(id)
            );
            
            INSERT INTO departamentos (id, nombre) VALUES (1, 'Ventas');
            INSERT INTO empleados (id, nombre, activo, departamento_id) VALUES (1, 'Juan Perez', TRUE, 1);
            
            CREATE USER manager_rh;
            CREATE USER empleado_consulta;
            
            GRANT SELECT ON empresa.* TO empleado_consulta;
            GRANT INSERT ON empresa.empleados TO manager_rh;
            GRANT UPDATE ON empresa.empleados TO manager_rh;
            
            SELECT e.nombre, d.nombre 
            FROM empleados e JOIN departamentos d ON e.departamento_id = d.id 
            WHERE e.activo = TRUE;
            
            UPDATE empleados SET activo = FALSE WHERE departamento_id = 1;
            
            DECLARE total_empleados INTEGER;
            DECLARE nombre_departamento VARCHAR(100);""";
        AnalyzerSQL analyzer = new AnalyzerSQL(textToAnalyze);
        analyzer.analyzer();
        Assertions.assertThat(analyzer.isError()).isFalse();
    }

    @Test
    void dclWithDmlCombination() {
        String textToAnalyze = """
            CREATE USER operador;
            CREATE USER supervisor;
            
            GRANT SELECT ON ventas.productos TO operador;
            GRANT INSERT ON ventas.productos TO supervisor;
            GRANT UPDATE ON ventas.productos TO supervisor;
            
            SELECT nombre, precio FROM ventas.productos WHERE activo = TRUE;
            INSERT INTO ventas.productos (id, nombre, precio) VALUES (1, 'Nuevo Producto', 1000);
            UPDATE ventas.productos SET precio = 1200 WHERE id = 1;
            
            REVOKE INSERT ON ventas.productos FROM supervisor;""";
        AnalyzerSQL analyzer = new AnalyzerSQL(textToAnalyze);
        analyzer.analyzer();
        Assertions.assertThat(analyzer.isError()).isFalse();
    }

    @Test
    void declareWithDclOperations() {
        String textToAnalyze = """
            DECLARE usuario_actual VARCHAR(100);
            DECLARE permisos_otorgados INTEGER;
            
            CREATE USER nuevo_usuario;
            GRANT SELECT ON sistema.* TO nuevo_usuario;
            
            DECLARE resultado_operacion BOOLEAN;""";
        AnalyzerSQL analyzer = new AnalyzerSQL(textToAnalyze);
        analyzer.analyzer();
        Assertions.assertThat(analyzer.isError()).isFalse();
    }

    // ====== DCL Error Cases Tests ======

    @Test
    void createUserMissingName() {
        String textToAnalyze = """
            CREATE USER;"""; // Falta nombre de usuario
        AnalyzerSQL analyzer = new AnalyzerSQL(textToAnalyze);
        analyzer.analyzer();
        Assertions.assertThat(analyzer.isError()).isTrue();
    }

    @Test
    void grantMissingPermission() {
        String textToAnalyze = """
            GRANT ON ventas.clientes TO usuario;"""; // Falta tipo de permiso
        AnalyzerSQL analyzer = new AnalyzerSQL(textToAnalyze);
        analyzer.analyzer();
        Assertions.assertThat(analyzer.isError()).isTrue();
    }

    @Test
    void grantMissingTable() {
        String textToAnalyze = """
            GRANT SELECT TO usuario;"""; // Falta ON tabla
        AnalyzerSQL analyzer = new AnalyzerSQL(textToAnalyze);
        analyzer.analyzer();
        Assertions.assertThat(analyzer.isError()).isTrue();
    }

    @Test
    void grantMissingUser() {
        String textToAnalyze = """
            GRANT SELECT ON ventas.clientes TO;"""; // Falta nombre de usuario
        AnalyzerSQL analyzer = new AnalyzerSQL(textToAnalyze);
        analyzer.analyzer();
        Assertions.assertThat(analyzer.isError()).isTrue();
    }

    @Test
    void revokeMissingFrom() {
        String textToAnalyze = """
            REVOKE SELECT ON ventas.clientes usuario;"""; // Falta FROM
        AnalyzerSQL analyzer = new AnalyzerSQL(textToAnalyze);
        analyzer.analyzer();
        Assertions.assertThat(analyzer.isError()).isTrue();
    }

    @Test
    void revokeMissingPermission() {
        String textToAnalyze = """
            REVOKE ON ventas.clientes FROM usuario;"""; // Falta tipo de permiso
        AnalyzerSQL analyzer = new AnalyzerSQL(textToAnalyze);
        analyzer.analyzer();
        Assertions.assertThat(analyzer.isError()).isTrue();
    }

    @Test
    void declareMissingType() {
        String textToAnalyze = """
            DECLARE variable_sin_tipo;"""; // Falta tipo de dato
        AnalyzerSQL analyzer = new AnalyzerSQL(textToAnalyze);
        analyzer.analyzer();
        Assertions.assertThat(analyzer.isError()).isTrue();
    }

    @Test
    void declareMissingName() {
        String textToAnalyze = """
            DECLARE INTEGER;"""; // Falta nombre de variable
        AnalyzerSQL analyzer = new AnalyzerSQL(textToAnalyze);
        analyzer.analyzer();
        Assertions.assertThat(analyzer.isError()).isTrue();
    }

    @Test
    void invalidPermissionType() {
        String textToAnalyze = """
            GRANT INVALID_PERMISSION ON ventas.clientes TO usuario;"""; // Permiso inválido
        AnalyzerSQL analyzer = new AnalyzerSQL(textToAnalyze);
        analyzer.analyzer();
        Assertions.assertThat(analyzer.isError()).isTrue();
    }

    @Test
    void grantWithoutSchemaQualification() {
        String textToAnalyze = """
            GRANT SELECT ON clientes TO usuario;"""; // Tabla sin esquema (podría ser error según implementación)
        AnalyzerSQL analyzer = new AnalyzerSQL(textToAnalyze);
        analyzer.analyzer();
        // Nota: Este podría ser válido o inválido dependiendo de si se requiere schema.tabla
        Assertions.assertThat(analyzer.isError()).isFalse();
    }

    // ====== Edge Cases Tests ======

    @Test
    void mixedCaseKeywords() {
        String textToAnalyze = """
            create user test_user;
            Create User another_user;""";
        AnalyzerSQL analyzer = new AnalyzerSQL(textToAnalyze);
        analyzer.analyzer();
        // Dependiendo de si el analizador es case-sensitive o no
        // Este test puede ser .isTrue() o .isFalse()
        Assertions.assertThat(analyzer.isError()).isTrue();
    }

    @Test
    void grantWithComplexSchemaNames() {
        String textToAnalyze = """
            GRANT SELECT ON esquema_muy_largo_123.tabla_con_nombre_largo TO usuario_con_nombre_muy_largo_2024;""";
        AnalyzerSQL analyzer = new AnalyzerSQL(textToAnalyze);
        analyzer.analyzer();
        Assertions.assertThat(analyzer.isError()).isFalse();
    }

    @Test
    void multipleGrantsToSameUser() {
        String textToAnalyze = """
            CREATE USER usuario_multiple;
            GRANT SELECT ON ventas.clientes TO usuario_multiple;
            GRANT INSERT ON ventas.clientes TO usuario_multiple;
            GRANT UPDATE ON ventas.clientes TO usuario_multiple;
            GRANT DELETE ON ventas.clientes TO usuario_multiple;""";
        AnalyzerSQL analyzer = new AnalyzerSQL(textToAnalyze);
        analyzer.analyzer();
        Assertions.assertThat(analyzer.isError()).isFalse();
    }

    @Test
    void consecutiveRevokeOperations() {
        String textToAnalyze = """
            REVOKE SELECT ON ventas.clientes FROM usuario1;
            REVOKE INSERT ON ventas.productos FROM usuario2;
            REVOKE UPDATE ON inventario.stock FROM usuario3;
            REVOKE DELETE ON sistema.logs FROM usuario4;""";
        AnalyzerSQL analyzer = new AnalyzerSQL(textToAnalyze);
        analyzer.analyzer();
        Assertions.assertThat(analyzer.isError()).isFalse();
    }

    @Test
    void declareVariablesWithComplexNames() {
        String textToAnalyze = """
            DECLARE _variable_muy_larga_con_underscores INTEGER;
            DECLARE contador_principal_sistema VARCHAR(200);
            DECLARE estado_operacion_compleja BOOLEAN;""";
        AnalyzerSQL analyzer = new AnalyzerSQL(textToAnalyze);
        analyzer.analyzer();
        Assertions.assertThat(analyzer.isError()).isFalse();
    }
}
