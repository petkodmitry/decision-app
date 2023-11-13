package org.inbank.petko.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Credit decision information to be responded for User
 * This class is immutable
 * @author Dmitry Petko (mailto:petkodmitry@gmail.com)
 */
@Getter
public class DecisionDto extends ErrorDto {

    public enum DecisionStatus {
        NEGATIVE,
        POSITIVE,
        PROPOSE_TERM,
        ACTIVE_LOAN
    }
    private final DecisionStatus decisionStatus;
    private final double sum;
    private final double term;
    private final String infoMsg;
    @JsonIgnore
    private final String errorMsg;

    /**
     * AllArgsConstructor with custom logic. Additionally, need to populate Info message
     * @param decisionStatus what decision came from Decision engine
     * @param sum            sum to propose
     * @param term           term to propose
     * @param locale         current {@link jakarta.servlet.http.HttpServletRequest} Locale
     */
    public DecisionDto(DecisionStatus decisionStatus, double sum, double term, Locale locale) {
        super(null);
        this.decisionStatus = decisionStatus;
        this.sum = sum;
        this.term = term;
        infoMsg = populateInfoMessage(locale);
        errorMsg = null;
    }

    @JsonIgnore
    private ResourceBundle resourceBundle;

    private String populateInfoMessage(Locale locale) {
        resourceBundle = ResourceBundle.getBundle("messages", locale);
        return switch (decisionStatus) {
            case NEGATIVE -> String.format(resourceBundle.getString("decision.negative"), sum, term, "");
            case POSITIVE -> String.format(resourceBundle.getString("decision.positive"), sum, term);
            case PROPOSE_TERM -> String.format(resourceBundle.getString("decision.propose.term"), sum, term);
            case ACTIVE_LOAN -> String.format(resourceBundle.getString("decision.negative"), sum, term,
                    resourceBundle.getString("decision.active.loan"));
        };
    }
}
