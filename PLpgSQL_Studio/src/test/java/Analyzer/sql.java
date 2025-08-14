package Analyzer;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import com.cunoc.drymnz.plpgsql_studio.c_interfaces.analyzer.AnalyzerSQL;

public class sql {
     @Test
     void nothingToAnalyze() {
          String nothingToAnalyze = "";
          AnalyzerSQL analyzer = new AnalyzerSQL(nothingToAnalyze);
          analyzer.analyzer();
          Assertions.assertThat(analyzer.isError());
     }
}
