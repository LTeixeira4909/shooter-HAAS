package frc.robot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.ShooterConstants;

public class Shooter extends SubsystemBase {
    private CANSparkMax motor1, motor2, motor3, motor4, motor5;
    private shooterStates m_angleSetpoint;

    private shooterStates m_currentState, m_lastState;

    public enum shooterStates {
        SHOOTER_RETRACTED("Retracted"),
        SHOOTER_LOW("Low"),
        SHOOTER_MID("Mid"),
        SHOOTER_HIGH("High");

        String stateName;

        private shooterStates(String name) {
            this.stateName = name;
        }

        public String toString() {
            return stateName;
        }
    }

    public Shooter(int motor1ID, int motor2ID, int motor3ID, int motor4ID, int motor5ID) {
        motor1 = new CANSparkMax(motor1ID, MotorType.kBrushless);
        motor2 = new CANSparkMax(motor2ID, MotorType.kBrushless);
        motor3 = new CANSparkMax(motor3ID, MotorType.kBrushless);
        motor4 = new CANSparkMax(motor4ID, MotorType.kBrushless);
        motor5 = new CANSparkMax(motor5ID, MotorType.kBrushless);
        m_currentState = shooterStates.SHOOTER_RETRACTED;
    }

    @Override
    public void periodic() {
        stateMachine();
    }

    private void stateMachine() {

        if (m_currentState.equals(m_lastState)) {
            return;
        }

        m_lastState = m_currentState;
        switch (m_currentState) {
            case SHOOTER_HIGH:
                m_angleSetpoint = shooterStates.SHOOTER_HIGH;
                break;
            case SHOOTER_MID:
                m_angleSetpoint = shooterStates.SHOOTER_MID;
                break;
            case SHOOTER_LOW:
                m_angleSetpoint = shooterStates.SHOOTER_LOW;
                break;
            case SHOOTER_RETRACTED:
                m_angleSetpoint = shooterStates.SHOOTER_RETRACTED;
                break;

        }
    }

    public void setshooterState(shooterStates state) {
        m_currentState = state;
    }

    public void shoot() {
        motor1.set(ShooterConstants.FLYWHEELESPEED);
        motor2.set(ShooterConstants.FLYWHEELESPEED);
        motor3.set(ShooterConstants.FLYWHEELESPEED);
        motor4.set(ShooterConstants.SPEED);
        motor5.set(ShooterConstants.SPEED);
    }

    public void flyWheeleStop() {
        motor1.set(ShooterConstants.STOP);
        motor2.set(ShooterConstants.STOP);
        motor3.set(ShooterConstants.STOP);

    }

}
