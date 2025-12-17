// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Robot;

import java.util.function.DoubleSupplier;

import javax.xml.stream.events.EndDocument;

import com.ctre.phoenix6.configs.CANdleConfiguration;
import com.ctre.phoenix6.controls.EmptyAnimation;
import com.ctre.phoenix6.controls.RainbowAnimation;
import com.ctre.phoenix6.hardware.CANdle;
import com.ctre.phoenix6.controls.SolidColor;
import com.ctre.phoenix6.signals.RGBWColor;

import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Timer;

public class LedCANdle extends SubsystemBase {
  private static CANdle candle = new CANdle(50, "Default Name"); //TODO: change id
  private static CANdleConfiguration config = new CANdleConfiguration();
  private static final int END_INDEX = 200;

  private static EmptyAnimation empty = new EmptyAnimation(0);
  private static RainbowAnimation rainbow = new RainbowAnimation(0, END_INDEX);
  
  private static Timer timer = new Timer();
  private static int fps = 20;
  private static double[] color = {255, 255, 255};

  /** Creates a new CANdle. */
  public LedCANdle() {
    candle.getConfigurator().apply(config);
    candle.setControl(empty);
    candle.setControl(new SolidColor(0, END_INDEX).withColor(new RGBWColor(0, 255, 0)));

    SmartDashboard.putNumberArray("Color {r, g, b}", color);
    
    timer.restart();
  }

  public void setLedColor(int r, int g, int b) {
    candle.setControl(empty);
    candle.setControl(new SolidColor(0, END_INDEX).withColor(new RGBWColor(r, g, b)));
  }

  public void setRainbowAnimation() {
    candle.setControl(empty);
    candle.setControl(rainbow);
  }

  public void colorWithBrightness(DoubleSupplier brightness) {
    // System.out.println(brightness.getAsDouble());
    double scale = brightness.getAsDouble();
    candle.setControl(empty);
    candle.setControl(new SolidColor(0, END_INDEX)
      .withColor(new RGBWColor(
        (int) (color[0] * scale),
        (int) (color[1] * scale),
        (int) (color[2] * scale)
      )
    ));
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    try {
      // color = SmartDashboard.getNumberArray("Color {r, g, b}", {255, 255, 255});
    } catch(Exception e) {
      System.out.println("somethings wrong with the led colors womp womp. error: " + e);
    }
    /*
    if((joystickView) && (timer.get() >= (1.0 / fps))) {
      double whiteFactor = ((joystick.getLeftX() * -1) + 1) / 2;
      candle.setControl(new SolidColor(0, 7)
        .withColor(new RGBWColor(
          (int) color[0],
          (int) color[1],
          (int) color[2],
          (int)(whiteFactor * 255))
        )
      );

      timer.restart();
    }
    */
  }
}
