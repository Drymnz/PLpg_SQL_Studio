package Analyzer;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import com.cunoc.drymnz.plpgsql_studio.c_interfaces.analyzer.AnalyzerSQL;

public class SQLTestDML {

    // ====== INSERT Tests ======

    @Test
    void insertSimple() {
        String textToAnalyze = """
            INSERT INTO clientes (id, nombre, activo) VALUES (1, 'Juan Perez', TRUE);""";
        AnalyzerSQL analyzer = new AnalyzerSQL(textToAnalyze);
        analyzer.analyzer();
        Assertions.assertThat(analyzer.isError()).isFalse();
    }

    @Test
    void insertMultiple() {
        String textToAnalyze = """
            INSERT INTO clientes (id, nombre, activo) VALUES (1, 'Juan Perez', TRUE);
            INSERT INTO productos (id, nombre, precio) VALUES (1, 'Laptop', 15000);
            INSERT INTO categorias (id, nombre) VALUES (1, 'Electronica');""";
        AnalyzerSQL analyzer = new AnalyzerSQL(textToAnalyze);
        analyzer.analyzer();
        Assertions.assertThat(analyzer.isError()).isFalse();
    }

    @Test
    void insertWithNullValues() {
        String textToAnalyze = """
            INSERT INTO productos (id, nombre, precio, categoria_id) VALUES (1, 'Mouse', NULL, 2);""";
        AnalyzerSQL analyzer = new AnalyzerSQL(textToAnalyze);
        analyzer.analyzer();
        Assertions.assertThat(analyzer.isError()).isFalse();
    }

    @Test
    void insertMinimalColumns() {
        String textToAnalyze = """
            INSERT INTO clientes (id, nombre) VALUES (2, 'Maria Lopez');""";
        AnalyzerSQL analyzer = new AnalyzerSQL(textToAnalyze);
        analyzer.analyzer();
        Assertions.assertThat(analyzer.isError()).isFalse();
    }

    // ====== UPDATE Tests ======

    @Test
    void updateSimple() {
        String textToAnalyze = """
            UPDATE clientes SET activo = FALSE WHERE id = 1;""";
        AnalyzerSQL analyzer = new AnalyzerSQL(textToAnalyze);
        analyzer.analyzer();
        Assertions.assertThat(analyzer.isError()).isFalse();
    }

    @Test
    void updateMultiple() {
        String textToAnalyze = """
            UPDATE clientes SET activo = FALSE WHERE id = 1;
            UPDATE productos SET precio = 18000 WHERE nombre = 'Laptop';
            UPDATE categorias SET nombre = 'Tecnologia' WHERE id = 1;""";
        AnalyzerSQL analyzer = new AnalyzerSQL(textToAnalyze);
        analyzer.analyzer();
        Assertions.assertThat(analyzer.isError()).isFalse();
    }

    @Test
    void updateMultipleColumns() {
        String textToAnalyze = """
            UPDATE productos SET precio = 16000, nombre = 'Laptop Pro' WHERE id = 1;""";
        AnalyzerSQL analyzer = new AnalyzerSQL(textToAnalyze);
        analyzer.analyzer();
        Assertions.assertThat(analyzer.isError()).isFalse();
    }

    @Test
    void updateWithLogicalOperators() {
        String textToAnalyze = """
            UPDATE productos SET precio = 2000 WHERE categoria_id = 1 AND precio = 1500;
            UPDATE clientes SET activo = TRUE WHERE nombre = 'Juan' OR nombre = 'Maria';""";
        AnalyzerSQL analyzer = new AnalyzerSQL(textToAnalyze);
        analyzer.analyzer();
        Assertions.assertThat(analyzer.isError()).isFalse();
    }

    // ====== DELETE Tests ======

    @Test
    void deleteSimple() {
        String textToAnalyze = """
            DELETE FROM productos WHERE precio = 0;""";
        AnalyzerSQL analyzer = new AnalyzerSQL(textToAnalyze);
        analyzer.analyzer();
        Assertions.assertThat(analyzer.isError()).isFalse();
    }

    @Test
    void deleteMultiple() {
        String textToAnalyze = """
            DELETE FROM productos WHERE precio = 0;
            DELETE FROM clientes WHERE activo = FALSE;
            DELETE FROM categorias WHERE nombre = 'Obsoleta';""";
        AnalyzerSQL analyzer = new AnalyzerSQL(textToAnalyze);
        analyzer.analyzer();
        Assertions.assertThat(analyzer.isError()).isFalse();
    }

    @Test
    void deleteWithLogicalOperators() {
        String textToAnalyze = """
            DELETE FROM productos WHERE precio = 0 AND categoria_id = 1;
            DELETE FROM clientes WHERE activo = FALSE OR nombre = 'Test';""";
        AnalyzerSQL analyzer = new AnalyzerSQL(textToAnalyze);
        analyzer.analyzer();
        Assertions.assertThat(analyzer.isError()).isFalse();
    }

    @Test
    void deleteByForeignKey() {
        String textToAnalyze = """
            DELETE FROM productos WHERE cliente_id = 1;""";
        AnalyzerSQL analyzer = new AnalyzerSQL(textToAnalyze);
        analyzer.analyzer();
        Assertions.assertThat(analyzer.isError()).isFalse();
    }

    // ====== SELECT Tests ======

    @Test
    void selectSimple() {
        String textToAnalyze = """
            SELECT id, nombre FROM clientes;""";
        AnalyzerSQL analyzer = new AnalyzerSQL(textToAnalyze);
        analyzer.analyzer();
        Assertions.assertThat(analyzer.isError()).isFalse();
    }

    @Test
    void selectAll() {
        String textToAnalyze = """
            SELECT * FROM productos;""";
        AnalyzerSQL analyzer = new AnalyzerSQL(textToAnalyze);
        analyzer.analyzer();
        Assertions.assertThat(analyzer.isError()).isFalse();
    }

    @Test
    void selectWithWhere() {
        String textToAnalyze = """
            SELECT nombre, precio FROM productos WHERE precio = 15000;
            SELECT id, nombre, activo FROM clientes WHERE activo = TRUE;""";
        AnalyzerSQL analyzer = new AnalyzerSQL(textToAnalyze);
        analyzer.analyzer();
        Assertions.assertThat(analyzer.isError()).isFalse();
    }

    @Test
    void selectWithLogicalOperators() {
        String textToAnalyze = """
            SELECT * FROM productos WHERE precio = 1000 AND activo = TRUE;
            SELECT * FROM clientes WHERE nombre = 'Juan' OR nombre = 'Maria';""";
        AnalyzerSQL analyzer = new AnalyzerSQL(textToAnalyze);
        analyzer.analyzer();
        Assertions.assertThat(analyzer.isError()).isFalse();
    }

    @Test
    void selectWithMultipleConditions() {
        String textToAnalyze = """
            SELECT nombre, precio FROM productos WHERE precio = 1500 AND categoria_id = 1 AND activo = TRUE;""";
        AnalyzerSQL analyzer = new AnalyzerSQL(textToAnalyze);
        analyzer.analyzer();
        Assertions.assertThat(analyzer.isError()).isFalse();
    }

    // ====== JOIN Tests ======

    @Test
    void selectWithJoin() {
        String textToAnalyze = """
            SELECT clientes.nombre, productos.nombre 
            FROM clientes JOIN productos ON clientes.id = productos.cliente_id;""";
        AnalyzerSQL analyzer = new AnalyzerSQL(textToAnalyze);
        analyzer.analyzer();
        Assertions.assertThat(analyzer.isError()).isFalse();
    }

    @Test
    void selectWithLeftJoin() {
        String textToAnalyze = """
            SELECT c.nombre, p.precio 
            FROM clientes c LEFT JOIN productos p ON c.id = p.cliente_id;""";
        AnalyzerSQL analyzer = new AnalyzerSQL(textToAnalyze);
        analyzer.analyzer();
        Assertions.assertThat(analyzer.isError()).isFalse();
    }

    @Test
    void selectWithJoinAndWhere() {
        String textToAnalyze = """
            SELECT c.nombre, p.nombre, p.precio 
            FROM clientes c JOIN productos p ON c.id = p.cliente_id 
            WHERE p.precio = 15000 AND c.activo = TRUE;""";
        AnalyzerSQL analyzer = new AnalyzerSQL(textToAnalyze);
        analyzer.analyzer();
        Assertions.assertThat(analyzer.isError()).isFalse();
    }

    @Test
    void selectWithMultipleJoins() {
        String textToAnalyze = """
            SELECT c.nombre, p.nombre, cat.nombre 
            FROM clientes c 
            JOIN productos p ON c.id = p.cliente_id 
            JOIN categorias cat ON p.categoria_id = cat.id;""";
        AnalyzerSQL analyzer = new AnalyzerSQL(textToAnalyze);
        analyzer.analyzer();
        Assertions.assertThat(analyzer.isError()).isFalse();
    }

    // ====== Schema-qualified Operations ======

    @Test
    void insertWithSchemaQualifiedTable() {
        String textToAnalyze = """
            INSERT INTO ventas.clientes (id, nombre) VALUES (1, 'Cliente A');
            INSERT INTO inventario.productos (id, stock) VALUES (1, 100);""";
        AnalyzerSQL analyzer = new AnalyzerSQL(textToAnalyze);
        analyzer.analyzer();
        Assertions.assertThat(analyzer.isError()).isFalse();
    }

    @Test
    void selectWithSchemaQualifiedTable() {
        String textToAnalyze = """
            SELECT nombre FROM ventas.clientes WHERE activo = TRUE;
            SELECT stock FROM inventario.productos WHERE stock = 0;""";
        AnalyzerSQL analyzer = new AnalyzerSQL(textToAnalyze);
        analyzer.analyzer();
        Assertions.assertThat(analyzer.isError()).isFalse();
    }

    @Test
    void updateWithSchemaQualifiedTable() {
        String textToAnalyze = """
            UPDATE ventas.clientes SET activo = FALSE WHERE id = 1;
            UPDATE inventario.productos SET stock = 50 WHERE id = 1;""";
        AnalyzerSQL analyzer = new AnalyzerSQL(textToAnalyze);
        analyzer.analyzer();
        Assertions.assertThat(analyzer.isError()).isFalse();
    }

    @Test
    void deleteWithSchemaQualifiedTable() {
        String textToAnalyze = """
            DELETE FROM ventas.clientes WHERE activo = FALSE;
            DELETE FROM inventario.productos WHERE stock = 0;""";
        AnalyzerSQL analyzer = new AnalyzerSQL(textToAnalyze);
        analyzer.analyzer();
        Assertions.assertThat(analyzer.isError()).isFalse();
    }

    // ====== Complex DML Operations ======

    @Test
    void complexDMLOperations() {
        String textToAnalyze = """
            INSERT INTO categorias (id, nombre) VALUES (1, 'Electronica');
            INSERT INTO productos (id, nombre, precio, activo, categoria_id) 
            VALUES (1, 'Smartphone', 25000, TRUE, 1);
            
            UPDATE productos SET precio = 22000 WHERE id = 1;
            
            SELECT p.nombre, c.nombre 
            FROM productos p JOIN categorias c ON p.categoria_id = c.id 
            WHERE p.activo = TRUE;
            
            DELETE FROM productos WHERE precio = 0;""";
        AnalyzerSQL analyzer = new AnalyzerSQL(textToAnalyze);
        analyzer.analyzer();
        Assertions.assertThat(analyzer.isError()).isFalse();
    }

    // ====== DML Error Cases Tests ======

    @Test
    void insertMissingValues() {
        String textToAnalyze = """
            INSERT INTO clientes (id, nombre) VALUES;"""; // Faltan valores
        AnalyzerSQL analyzer = new AnalyzerSQL(textToAnalyze);
        analyzer.analyzer();
        Assertions.assertThat(analyzer.isError()).isTrue();
    }

    @Test
    void updateMissingSet() {
        String textToAnalyze = """
            UPDATE clientes WHERE id = 1;"""; // Falta SET
        AnalyzerSQL analyzer = new AnalyzerSQL(textToAnalyze);
        analyzer.analyzer();
        Assertions.assertThat(analyzer.isError()).isTrue();
    }

    @Test
    void selectMissingFrom() {
        String textToAnalyze = """
            SELECT id, nombre;"""; // Falta FROM
        AnalyzerSQL analyzer = new AnalyzerSQL(textToAnalyze);
        analyzer.analyzer();
        Assertions.assertThat(analyzer.isError()).isTrue();
    }

    @Test
    void deleteMissingFrom() {
        String textToAnalyze = """
            DELETE clientes WHERE id = 1;"""; // Falta FROM
        AnalyzerSQL analyzer = new AnalyzerSQL(textToAnalyze);
        analyzer.analyzer();
        Assertions.assertThat(analyzer.isError()).isTrue();
    }

    @Test
    void joinMissingOn() {
        String textToAnalyze = """
            SELECT * FROM clientes JOIN productos;"""; // Falta ON
        AnalyzerSQL analyzer = new AnalyzerSQL(textToAnalyze);
        analyzer.analyzer();
        Assertions.assertThat(analyzer.isError()).isTrue();
    }
}
