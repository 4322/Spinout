package frc.utility.spinout;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;

public class SpinoutUtil {
  
  public enum SpinoutDirection {
    CW, CCW
  }

  public enum Wheel {
    FL, FR, BL, BR
  }

  private enum SpinoutZone {
    FRONT, LEFT, RIGHT, BACK, FL, FR, BL, BR
  }

  public static Wheel calcWheelToLock(Translation2d fieldRelativeInputs, Rotation2d robotAngle, SpinoutDirection dir) {
    return calcWheelToLock(fieldRelativeInputs, robotAngle, dir, 30);
  }

  public static Wheel calcWheelToLock(Translation2d fieldRelativeInputs, Rotation2d robotAngle, SpinoutDirection dir, double cornerSpanDeg) {
    // get spinout "zone" based on robot relative angle
    Translation2d robotRelativeInputs = fieldRelativeInputs.rotateBy(robotAngle.unaryMinus());
    SpinoutZone zone = getSpinoutZone(robotRelativeInputs.getAngle(), cornerSpanDeg);

    switch (zone) {
      case FRONT:
        return (dir == SpinoutDirection.CW) ? Wheel.FL : Wheel.FR;
      case LEFT:
        return (dir == SpinoutDirection.CW) ? Wheel.BL : Wheel.FL;
      case BACK:
        return (dir == SpinoutDirection.CW) ? Wheel.BR : Wheel.BL;
      case RIGHT:
        return (dir == SpinoutDirection.CW) ? Wheel.FR : Wheel.BR;
      case FL:
        return Wheel.FL;
      case FR:
        return Wheel.FR;
      case BL:
        return Wheel.BL;
      case BR:
        return Wheel.BR;
    }

    return Wheel.FL;
  }
  
  private static SpinoutZone getSpinoutZone(Rotation2d robotRelativeInputsAngle, double cornerSpan) {
    // sanitize inputs
    double boundedAngleDeg = MathUtil.inputModulus(robotRelativeInputsAngle.getDegrees(), 0, 360);
    if (cornerSpan < 0) {
      cornerSpan = 0;
    } else if (cornerSpan > 90) {
      cornerSpan = 90;
    }

    // man, i wish there was an easier way to do this
    // in all seriousness there is but this way is clear and prevents bloat in other places
    double straightSpan = 90 - cornerSpan;
    if (boundedAngleDeg <= (straightSpan * 0.5)) {
      return SpinoutZone.FRONT;
    } else if (boundedAngleDeg <= (straightSpan * 0.5 + cornerSpan)) {
      return SpinoutZone.FL;
    } else if (boundedAngleDeg <= (straightSpan * 1.5 + cornerSpan)) {
      return SpinoutZone.LEFT;
    } else if (boundedAngleDeg <= (straightSpan * 1.5 + cornerSpan * 2)) {
      return SpinoutZone.BL;
    } else if (boundedAngleDeg <= (straightSpan * 2.5 + cornerSpan * 2)) {
      return SpinoutZone.BACK;
    } else if (boundedAngleDeg <= (straightSpan * 2.5 + cornerSpan * 3)) {
      return SpinoutZone.BR;
    } else if (boundedAngleDeg <= (straightSpan * 3.5 + cornerSpan * 3)) {
      return SpinoutZone.RIGHT;
    } else if (boundedAngleDeg <= (straightSpan * 3.5 + cornerSpan * 4)) {
      return SpinoutZone.FR;
    } else {
      return SpinoutZone.FRONT;
    }

  }

}
