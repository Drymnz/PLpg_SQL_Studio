package Analyzer;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import com.cunoc.drymnz.plpgsql_studio.c_interfaces.analyzer.AnalyzerSQL;

public class SQLTestAll {
    
    @Test
    void test01_Comments() {
        String textToAnalyze = """
            -- Comentario de línea simple
            /* 
               Comentario de bloque
               que puede abarcar 
               múltiples líneas
            */
           """;
        AnalyzerSQL analyzer = new AnalyzerSQL(textToAnalyze);
        analyzer.analyzer();
        Assertions.assertThat(analyzer.isError()).isFalse();
    }

    @Test
    void test02_CreateSchema() {
        String textToAnalyze = """
            CREATE SCHEMA tienda;
            CREATE SCHEMA usuarios_sistema;
           """;
        AnalyzerSQL analyzer = new AnalyzerSQL(textToAnalyze);
        analyzer.analyzer();
        Assertions.assertThat(analyzer.isError()).isFalse();
    }

    @Test
    void test03_UseSchema() {
        String textToAnalyze = """
            USE SCHEMA tienda;
           """;
        AnalyzerSQL analyzer = new AnalyzerSQL(textToAnalyze);
        analyzer.analyzer();
        Assertions.assertThat(analyzer.isError()).isFalse();
    }

    @Test
    void test04_CreateTableSimple() {
        String textToAnalyze = """
            CREATE TABLE clientes (
                id INTEGER PRIMARY KEY,
                nombre VARCHAR(100) NOT NULL
            );
           """;
        AnalyzerSQL analyzer = new AnalyzerSQL(textToAnalyze);
        analyzer.analyzer();
        Assertions.assertThat(analyzer.isError()).isFalse();
    }

    @Test
    void test05_CreateTableTypes() {
        String textToAnalyze = """
            CREATE TABLE productos (
                id INTEGER PRIMARY KEY,
                nombre VARCHAR(200) NOT NULL,
                precio DECIMAL NOT NULL,
                activo BOOLEAN NOT NULL,
                fecha DATE NULL
            );
           """;
        AnalyzerSQL analyzer = new AnalyzerSQL(textToAnalyze);
        analyzer.analyzer();
        Assertions.assertThat(analyzer.isError()).isFalse();
    }

    @Test
    void test06_CreateTableForeignKey() {
        String textToAnalyze = """
            CREATE TABLE pedidos (
                id INTEGER PRIMARY KEY,
                cliente_id INTEGER NOT NULL,
                total DECIMAL NOT NULL,
                FOREIGN KEY (cliente_id) REFERENCES clientes(id)
            );
           """;
        AnalyzerSQL analyzer = new AnalyzerSQL(textToAnalyze);
        analyzer.analyzer();
        Assertions.assertThat(analyzer.isError()).isFalse();
    }

    @Test
    void test07_AlterTableAddColumn() {
        String textToAnalyze = """
            ALTER TABLE clientes ADD COLUMN telefono VARCHAR(15);
           """;
        AnalyzerSQL analyzer = new AnalyzerSQL(textToAnalyze);
        analyzer.analyzer();
        Assertions.assertThat(analyzer.isError()).isFalse();
    }

    @Test
    void test08_AlterTableDropColumn() {
        String textToAnalyze = """
            ALTER TABLE productos DROP COLUMN descripcion;
           """;
        AnalyzerSQL analyzer = new AnalyzerSQL(textToAnalyze);
        analyzer.analyzer();
        Assertions.assertThat(analyzer.isError()).isFalse();
    }

    @Test
    void test09_AlterTableAddConstraint() {
        String textToAnalyze = """
            ALTER TABLE pedidos ADD CONSTRAINT fk_cliente_pedido 
                FOREIGN KEY (cliente_id) REFERENCES clientes(id);
           """;
        AnalyzerSQL analyzer = new AnalyzerSQL(textToAnalyze);
        analyzer.analyzer();
        Assertions.assertThat(analyzer.isError()).isFalse();
    }

    @Test
    void test10_DropTable() {
        String textToAnalyze = """
            DROP TABLE pedidos;
           """;
        AnalyzerSQL analyzer = new AnalyzerSQL(textToAnalyze);
        analyzer.analyzer();
        Assertions.assertThat(analyzer.isError()).isFalse();
    }

    @Test
    void test11_CreateUser() {
        String textToAnalyze = """
            CREATE USER admin_tienda;
            CREATE USER vendedor;
           """;
        AnalyzerSQL analyzer = new AnalyzerSQL(textToAnalyze);
        analyzer.analyzer();
        Assertions.assertThat(analyzer.isError()).isFalse();
    }

    @Test
    void test12_GrantBasic() {
        String textToAnalyze = """
            GRANT SELECT ON clientes TO vendedor;
           """;
        AnalyzerSQL analyzer = new AnalyzerSQL(textToAnalyze);
        analyzer.analyzer();
        Assertions.assertThat(analyzer.isError()).isFalse();
    }

    @Test
    void test13_GrantSchema() {
        String textToAnalyze = """
            GRANT SELECT ON tienda.* TO consultor;
           """;
        AnalyzerSQL analyzer = new AnalyzerSQL(textToAnalyze);
        analyzer.analyzer();
        Assertions.assertThat(analyzer.isError()).isFalse();
    }

    @Test
    void test14_Revoke() {
        String textToAnalyze = """
            REVOKE INSERT ON clientes FROM vendedor;
           """;
        AnalyzerSQL analyzer = new AnalyzerSQL(textToAnalyze);
        analyzer.analyzer();
        Assertions.assertThat(analyzer.isError()).isFalse();
    }

    @Test
    void test15_InsertSimple() {
        String textToAnalyze = """
            INSERT INTO clientes (id, nombre) VALUES (1, 'Juan Pérez');
           """;
        AnalyzerSQL analyzer = new AnalyzerSQL(textToAnalyze);
        analyzer.analyzer();
        Assertions.assertThat(analyzer.isError()).isFalse();
    }

    @Test
    void test16_InsertTypes() {
        String textToAnalyze = """
            INSERT INTO productos (id, nombre, precio, activo) 
            VALUES (1, 'Laptop Dell', 15000.00, true);
           """;
        AnalyzerSQL analyzer = new AnalyzerSQL(textToAnalyze);
        analyzer.analyzer();
        Assertions.assertThat(analyzer.isError()).isFalse();
    }

    @Test
    void test17_UpdateSimple() {
        String textToAnalyze = """
            UPDATE clientes SET nombre = 'Juan Carlos' WHERE id = 1;
           """;
        AnalyzerSQL analyzer = new AnalyzerSQL(textToAnalyze);
        analyzer.analyzer();
        Assertions.assertThat(analyzer.isError()).isFalse();
    }

    @Test
    void test18_DeleteSimple() {
        String textToAnalyze = """
            DELETE FROM clientes WHERE activo = false;
           """;
        AnalyzerSQL analyzer = new AnalyzerSQL(textToAnalyze);
        analyzer.analyzer();
        Assertions.assertThat(analyzer.isError()).isFalse();
    }


    @Test
    void test19_SelectSimple() {
        String textToAnalyze = """
            SELECT * FROM clientes;
           """;
        AnalyzerSQL analyzer = new AnalyzerSQL(textToAnalyze);
        analyzer.analyzer();
        Assertions.assertThat(analyzer.isError()).isFalse();
    }


    @Test
    void test20_SelectColumns() {
        String textToAnalyze = """
            SELECT nombre, email FROM clientes WHERE activo = true;
           """;
        AnalyzerSQL analyzer = new AnalyzerSQL(textToAnalyze);
        analyzer.analyzer();
        Assertions.assertThat(analyzer.isError()).isFalse();
    }


    @Test
    void test21_SelectJoin() {
        String textToAnalyze = """
            SELECT c.nombre, p.total 
            FROM clientes c 
            JOIN pedidos p ON c.id = p.cliente_id;
           """;
        AnalyzerSQL analyzer = new AnalyzerSQL(textToAnalyze);
        analyzer.analyzer();
        Assertions.assertThat(analyzer.isError()).isFalse();
    }


    @Test
    void test22_SelectLeftJoin() {
        String textToAnalyze = """
            SELECT c.nombre, p.total 
            FROM clientes c 
            LEFT JOIN pedidos p ON c.id = p.cliente_id;
           """;
        AnalyzerSQL analyzer = new AnalyzerSQL(textToAnalyze);
        analyzer.analyzer();
        Assertions.assertThat(analyzer.isError()).isFalse();
    }


    @Test
    void test23_SchemaSpecific() {
        String textToAnalyze = """
            INSERT INTO usuarios_sistema.administradores (id, username) 
            VALUES (1, 'admin');
           """;
        AnalyzerSQL analyzer = new AnalyzerSQL(textToAnalyze);
        analyzer.analyzer();
        Assertions.assertThat(analyzer.isError()).isFalse();
    }


    @Test
    void test24_MultipleStatements() {
        String textToAnalyze = """
            CREATE SCHEMA test;
            USE SCHEMA test;
            CREATE TABLE usuarios (id INTEGER PRIMARY KEY);
           """;
        AnalyzerSQL analyzer = new AnalyzerSQL(textToAnalyze);
        analyzer.analyzer();
        Assertions.assertThat(analyzer.isError()).isFalse();
    }


    @Test
    void test25_UnderscoreIdentifiers() {
        String textToAnalyze = """
            CREATE TABLE tabla_test (
                _id INTEGER PRIMARY KEY,
                nombre_completo VARCHAR(100),
                es_activo BOOLEAN
            );
           """;
        AnalyzerSQL analyzer = new AnalyzerSQL(textToAnalyze);
        analyzer.analyzer();
        Assertions.assertThat(analyzer.isError()).isFalse();
    }
}