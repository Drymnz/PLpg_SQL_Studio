package Analyzer;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import com.cunoc.drymnz.plpgsql_studio.c_interfaces.analyzer.AnalyzerSQL;

public class SQLTestDDL {
    
    @Test
    void nothingToAnalyze() {
        String textToAnalyze = "";
        AnalyzerSQL analyzer = new AnalyzerSQL(textToAnalyze);
        analyzer.analyzer();
        Assertions.assertThat(analyzer.isError()).isFalse();
    }

    // ====== CREATE SCHEMA Tests ======
    
    @Test
    void createSchema() {
        String textToAnalyze = """
            CREATE SCHEMA ventas;
            CREATE SCHEMA recursos_humanos;""";
        AnalyzerSQL analyzer = new AnalyzerSQL(textToAnalyze);
        analyzer.analyzer();
        System.out.println(analyzer.getListError().size());
        Assertions.assertThat(analyzer.isError()).isFalse();
    }
    
    @Test
    void createSchemaSingle() {
        String textToAnalyze = "CREATE SCHEMA inventario;";
        AnalyzerSQL analyzer = new AnalyzerSQL(textToAnalyze);
        analyzer.analyzer();
        Assertions.assertThat(analyzer.isError()).isFalse();
    }

    // ====== USE SCHEMA Tests ======
    
    @Test
    void useSchema() {
        String textToAnalyze = """
            USE SCHEMA ventas;
            USE SCHEMA recursos_humanos;""";
        AnalyzerSQL analyzer = new AnalyzerSQL(textToAnalyze);
        analyzer.analyzer();
        Assertions.assertThat(analyzer.isError()).isFalse();
    }

    @Test
    void useSchemaSingle() {
        String textToAnalyze = "USE SCHEMA tienda_online;";
        AnalyzerSQL analyzer = new AnalyzerSQL(textToAnalyze);
        analyzer.analyzer();
        Assertions.assertThat(analyzer.isError()).isFalse();
    }

    // ====== CREATE TABLE Tests ======

    @Test
    void createTableOneAttribute() {
        String textToAnalyze = """
            CREATE TABLE clientes (
                id INTEGER PRIMARY KEY
            );""";
        AnalyzerSQL analyzer = new AnalyzerSQL(textToAnalyze);
        analyzer.analyzer();
        Assertions.assertThat(analyzer.isError()).isFalse();
    }

    @Test
    void createTableSimple() {
        String textToAnalyze = """
            CREATE TABLE clientes (
                id INTEGER PRIMARY KEY,
                nombre VARCHAR(100) NOT NULL,
                activo BOOLEAN NULL
            );""";
        AnalyzerSQL analyzer = new AnalyzerSQL(textToAnalyze);
        analyzer.analyzer();
        Assertions.assertThat(analyzer.isError()).isFalse();
    }

    @Test
    void createTableWithForeignKey() {
        String textToAnalyze = """
            CREATE TABLE productos (
                id INTEGER PRIMARY KEY,
                nombre VARCHAR(50) NOT NULL,
                precio INTEGER,
                cliente_id INTEGER,
                FOREIGN KEY (cliente_id) REFERENCES clientes(id)
            );""";
        AnalyzerSQL analyzer = new AnalyzerSQL(textToAnalyze);
        analyzer.analyzer();
        Assertions.assertThat(analyzer.isError()).isFalse();
    }

    @Test
    void createTableMultipleForeignKeys() {
        String textToAnalyze = """
            CREATE TABLE pedidos (
                id INTEGER PRIMARY KEY,
                cliente_id INTEGER,
                producto_id INTEGER,
                cantidad INTEGER,
                FOREIGN KEY (cliente_id) REFERENCES clientes(id),
                FOREIGN KEY (producto_id) REFERENCES productos(id)
            );""";
        AnalyzerSQL analyzer = new AnalyzerSQL(textToAnalyze);
        analyzer.analyzer();
        Assertions.assertThat(analyzer.isError()).isFalse();
    }

    // ====== ALTER TABLE Tests ======

    @Test
    void alterTableAddColumn() {
        String textToAnalyze = """
            ALTER TABLE clientes ADD COLUMN telefono VARCHAR(20);
            ALTER TABLE productos ADD COLUMN categoria VARCHAR(50);""";
        AnalyzerSQL analyzer = new AnalyzerSQL(textToAnalyze);
        analyzer.analyzer();
        Assertions.assertThat(analyzer.isError()).isFalse();
    }

    @Test
    void alterTableAddColumnSingle() {
        String textToAnalyze = "ALTER TABLE empleados ADD COLUMN salario INTEGER;";
        AnalyzerSQL analyzer = new AnalyzerSQL(textToAnalyze);
        analyzer.analyzer();
        Assertions.assertThat(analyzer.isError()).isFalse();
    }

    @Test
    void alterTableDropColumn() {
        String textToAnalyze = """
            ALTER TABLE clientes DROP COLUMN telefono;
            ALTER TABLE productos DROP COLUMN categoria;""";
        AnalyzerSQL analyzer = new AnalyzerSQL(textToAnalyze);
        analyzer.analyzer();
        Assertions.assertThat(analyzer.isError()).isFalse();
    }

    @Test
    void alterTableDropColumnSingle() {
        String textToAnalyze = "ALTER TABLE empleados DROP COLUMN salario;";
        AnalyzerSQL analyzer = new AnalyzerSQL(textToAnalyze);
        analyzer.analyzer();
        Assertions.assertThat(analyzer.isError()).isFalse();
    }

    @Test
    void alterTableAddConstraint() {
        String textToAnalyze = """
            ALTER TABLE productos ADD CONSTRAINT fk_cliente FOREIGN KEY (cliente_id) REFERENCES clientes(id);""";
        AnalyzerSQL analyzer = new AnalyzerSQL(textToAnalyze);
        analyzer.analyzer();
        Assertions.assertThat(analyzer.isError()).isFalse();
    }

    @Test
    void alterTableAddMultipleConstraints() {
        String textToAnalyze = """
            ALTER TABLE pedidos ADD CONSTRAINT fk_cliente FOREIGN KEY (cliente_id) REFERENCES clientes(id);
            ALTER TABLE pedidos ADD CONSTRAINT fk_producto FOREIGN KEY (producto_id) REFERENCES productos(id);""";
        AnalyzerSQL analyzer = new AnalyzerSQL(textToAnalyze);
        analyzer.analyzer();
        Assertions.assertThat(analyzer.isError()).isFalse();
    }

    @Test
    void alterTableDropConstraint() {
        String textToAnalyze = """
            ALTER TABLE productos DROP CONSTRAINT fk_cliente;""";
        AnalyzerSQL analyzer = new AnalyzerSQL(textToAnalyze);
        analyzer.analyzer();
        Assertions.assertThat(analyzer.isError()).isFalse();
    }

    @Test
    void alterTableDropMultipleConstraints() {
        String textToAnalyze = """
            ALTER TABLE pedidos DROP CONSTRAINT fk_cliente;
            ALTER TABLE pedidos DROP CONSTRAINT fk_producto;""";
        AnalyzerSQL analyzer = new AnalyzerSQL(textToAnalyze);
        analyzer.analyzer();
        Assertions.assertThat(analyzer.isError()).isFalse();
    }

    // ====== DROP TABLE Tests ======

    @Test
    void dropTable() {
        String textToAnalyze = """
            DROP TABLE productos;
            DROP TABLE clientes;""";
        AnalyzerSQL analyzer = new AnalyzerSQL(textToAnalyze);
        analyzer.analyzer();
        Assertions.assertThat(analyzer.isError()).isFalse();
    }

    @Test
    void dropTableSingle() {
        String textToAnalyze = "DROP TABLE temporal;";
        AnalyzerSQL analyzer = new AnalyzerSQL(textToAnalyze);
        analyzer.analyzer();
        Assertions.assertThat(analyzer.isError()).isFalse();
    }

    // ====== Data Types Test ======

    @Test
    void testDataTypesINTEGER() {
        String textToAnalyze = """
            CREATE TABLE test_integer (
                id INTEGER PRIMARY KEY,
                cantidad INTEGER
            );""";
        AnalyzerSQL analyzer = new AnalyzerSQL(textToAnalyze);
        analyzer.analyzer();
        Assertions.assertThat(analyzer.isError()).isFalse();
    }

    @Test
    void testDataTypesINT() {
        String textToAnalyze = """
            CREATE TABLE test_int (
                id INT PRIMARY KEY,
                numero INT
            );""";
        AnalyzerSQL analyzer = new AnalyzerSQL(textToAnalyze);
        analyzer.analyzer();
        Assertions.assertThat(analyzer.isError()).isFalse();
    }

    @Test
    void testDataTypesVARCHAR() {
        String textToAnalyze = """
            CREATE TABLE test_varchar (
                id INTEGER PRIMARY KEY,
                nombre VARCHAR(100),
                descripcion VARCHAR(255)
            );""";
        AnalyzerSQL analyzer = new AnalyzerSQL(textToAnalyze);
        analyzer.analyzer();
        Assertions.assertThat(analyzer.isError()).isFalse();
    }

    @Test
    void testDataTypesBOOLEAN() {
        String textToAnalyze = """
            CREATE TABLE test_boolean (
                id INTEGER PRIMARY KEY,
                activo BOOLEAN,
                visible BOOLEAN NULL
            );""";
        AnalyzerSQL analyzer = new AnalyzerSQL(textToAnalyze);
        analyzer.analyzer();
        Assertions.assertThat(analyzer.isError()).isFalse();
    }

    @Test
    void testAllDataTypes() {
        String textToAnalyze = """
            CREATE TABLE test_tipos (
                id INTEGER PRIMARY KEY,
                numero INT,
                texto VARCHAR(255),
                activo BOOLEAN NOT NULL
            );""";
        AnalyzerSQL analyzer = new AnalyzerSQL(textToAnalyze);
        analyzer.analyzer();
        Assertions.assertThat(analyzer.isError()).isFalse();
    }

    // ====== Valid Identifiers Test ======

    @Test
    void validIdentifiers() {
        String textToAnalyze = """
            CREATE TABLE _tabla_privada (
                _id INTEGER PRIMARY KEY,
                nombre_completo VARCHAR(100),
                edad1 INTEGER,
                es_aprobado BOOLEAN
            );""";
        AnalyzerSQL analyzer = new AnalyzerSQL(textToAnalyze);
        analyzer.analyzer();
        Assertions.assertThat(analyzer.isError()).isFalse();
    }

    @Test
    void validIdentifiersSchema() {
        String textToAnalyze = """
            CREATE SCHEMA esquema_principal;
            CREATE SCHEMA _esquema_privado;
            CREATE SCHEMA sistema2024;""";
        AnalyzerSQL analyzer = new AnalyzerSQL(textToAnalyze);
        analyzer.analyzer();
        Assertions.assertThat(analyzer.isError()).isFalse();
    }

}
