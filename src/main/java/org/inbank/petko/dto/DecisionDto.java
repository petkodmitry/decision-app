package org.inbank.petko.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Credit decision information to be responded for User
 * @author Dmitry Petko (mailto:petkodmitry@gmail.com)
 */
@Setter @Getter
@NoArgsConstructor
public class DecisionDto extends ErrorDto {

    public enum DecisionStatus {
        NEGATIVE,
        POSITIVE,
        PROPOSE_TERM,
        ACTIVE_LOAN
    }
    private DecisionStatus decisionStatus;
    private double sum;
    private double term;
    private String infoMsg;
    @JsonIgnore
    private String errorMsg;

    /**
     * AllArgsConstructor with custom logic. Additionally, need to populate Info message
     * @param decisionStatus what decision came from Decision engine
     * @param sum            sum to propose
     * @param term           term to propose
     * @param locale         current {@link jakarta.servlet.http.HttpServletRequest} Locale
     */
    public DecisionDto(DecisionStatus decisionStatus, double sum, double term, Locale locale) {
        this.decisionStatus = decisionStatus;
        this.sum = sum;
        this.term = term;
        populateInfoMessage(locale);
    }

    @JsonIgnore
    private ResourceBundle resourceBundle;

    private void populateInfoMessage(Locale locale) {
        resourceBundle = ResourceBundle.getBundle("messages", locale);
        switch (decisionStatus) {
            case NEGATIVE -> infoMsg = String.format(
                    resourceBundle.getString("decision.negative"), sum, term, "");
            case POSITIVE -> infoMsg = String.format(
                    resourceBundle.getString("decision.positive"), sum, term);
            case PROPOSE_TERM -> infoMsg = String.format(
                    resourceBundle.getString("decision.propose.term"), sum, term);
            case ACTIVE_LOAN -> infoMsg = String.format(
                    resourceBundle.getString("decision.negative"), sum, term, resourceBundle.getString("decision.active.loan"));
        }
    }
}
