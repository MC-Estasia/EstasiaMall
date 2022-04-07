package de.mcestasia.estasiamall.setup;

import lombok.Getter;

import static de.mcestasia.estasiamall.util.Constants.PREFIX;

@Getter
public enum SetupStep {

    FIRST_STEP(PREFIX + "Bitte schlage nun den §e§luntersten §7Block des Shops!", SetupStepType.LOCATION_HIT, 1),
    SECOND_STEP(PREFIX + "Bitte schlage nun den §e§lobersten §7Block des Shops!", SetupStepType.LOCATION_HIT, 2),
    THIRD_STEP(PREFIX + "Bitte schlage nun das §e§lVerkaufs-Schild §7des Shops!", SetupStepType.LOCATION_HIT, 3),
    FOURTH_STEP(PREFIX + "Bitte §e§lstelle §7dich nun an die Stelle des Hologramms und schreibe §agesetzt §7in den Chat!", SetupStepType.LOCATION_STAND, 4);

    private final String setupStepMessage;
    private final SetupStepType setupStepType;
    private final int setupOrder;

    SetupStep(String setupStepMessage, SetupStepType setupStepType, int setupOrder) {
        this.setupStepMessage = setupStepMessage;
        this.setupStepType = setupStepType;
        this.setupOrder = setupOrder;
    }
}
