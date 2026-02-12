module com.baeldung.lnj.domain{
    requires transitive java.logging;
    exports com.baeldung.lnj.domain.model to com.baeldung.lnj.api;
    opens com.baeldung.lnj.domain.model to com.fasterxml.jackson.databind;
}