package com.cunoc.drymnz.plpgsql_studio.c_interfaces.analyzer;

import java.util.ArrayList;

import com.cunoc.drymnz.plpgsql_studio.a_entidades.analyzer.AnalyzerBase;
import com.cunoc.drymnz.plpgsql_studio.a_entidades.analyzer.ReportErrorInterpreter;
import com.cunoc.drymnz.plpgsql_studio.b_cases.analysis.sql.LexemaSQL;
import com.cunoc.drymnz.plpgsql_studio.b_cases.analysis.sql.ParserSQL;

public class AnalyzerSQL  extends AnalyzerBase{

    private final LexemaSQL lexema;
    private final ParserSQL parser;

    public AnalyzerSQL(String text) {
        this.lexema = new LexemaSQL(text);
        this.parser = new ParserSQL(lexema);
    }


    @Override
    protected void executeParse() throws Exception {
        parser.parse();
    }

    @Override
    public boolean isError() {
        return !lexema.getListError().isEmpty() || !parser.getListError().isEmpty();
    }

    @Override
    public ArrayList<ReportErrorInterpreter> getListError() {
        ArrayList<ReportErrorInterpreter> errorList = new ArrayList<>();
        errorList.addAll(lexema.getListError());
        errorList.addAll(parser.getListError());
        return errorList;
    }
    
}
