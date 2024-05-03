import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.*;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import frc.utility.spinout.SpinoutUtil;
import frc.utility.spinout.SpinoutUtil.SpinoutDirection;
import frc.utility.spinout.SpinoutUtil.Wheel;

public class SpinoutUtilTest {
  
  // visualization: https://www.desmos.com/calculator/anlzbu5hqy

  @BeforeEach
  public void setup() {
    return;
  }

  @AfterEach
  public void shutdown() {
    return;
  }

  @Test
  public void testCalcWheelToLock_TrivialCases() {
    // Driving straight, spinning clockwise
    assertEquals(Wheel.FL, SpinoutUtil.calcWheelToLock(new Translation2d(1, 0), Rotation2d.fromDegrees(0), SpinoutDirection.CW, 30));
    assertEquals(Wheel.FR, SpinoutUtil.calcWheelToLock(new Translation2d(1, 0), Rotation2d.fromDegrees(90), SpinoutDirection.CW, 30));
    assertEquals(Wheel.BR, SpinoutUtil.calcWheelToLock(new Translation2d(1, 0), Rotation2d.fromDegrees(180), SpinoutDirection.CW, 30));
    assertEquals(Wheel.BL, SpinoutUtil.calcWheelToLock(new Translation2d(1, 0), Rotation2d.fromDegrees(270), SpinoutDirection.CW, 30));

    // Driving straight, spinnng counter-clockwise
    assertEquals(Wheel.FR, SpinoutUtil.calcWheelToLock(new Translation2d(1, 0), Rotation2d.fromDegrees(0), SpinoutDirection.CCW, 30));
    assertEquals(Wheel.BR, SpinoutUtil.calcWheelToLock(new Translation2d(1, 0), Rotation2d.fromDegrees(90), SpinoutDirection.CCW, 30));
    assertEquals(Wheel.BL, SpinoutUtil.calcWheelToLock(new Translation2d(1, 0), Rotation2d.fromDegrees(180), SpinoutDirection.CCW, 30));
    assertEquals(Wheel.FL, SpinoutUtil.calcWheelToLock(new Translation2d(1, 0), Rotation2d.fromDegrees(270), SpinoutDirection.CCW, 30));

    // Corners (both directions should be the same)
    assertEquals(Wheel.FR, SpinoutUtil.calcWheelToLock(new Translation2d(1, 0), Rotation2d.fromDegrees(45), SpinoutDirection.CW, 30));
    assertEquals(Wheel.BR, SpinoutUtil.calcWheelToLock(new Translation2d(1, 0), Rotation2d.fromDegrees(135), SpinoutDirection.CW, 30));
    assertEquals(Wheel.BL, SpinoutUtil.calcWheelToLock(new Translation2d(1, 0), Rotation2d.fromDegrees(225), SpinoutDirection.CW, 30));
    assertEquals(Wheel.FL, SpinoutUtil.calcWheelToLock(new Translation2d(1, 0), Rotation2d.fromDegrees(315), SpinoutDirection.CW, 30));

    assertEquals(Wheel.FR, SpinoutUtil.calcWheelToLock(new Translation2d(1, 0), Rotation2d.fromDegrees(45), SpinoutDirection.CCW, 30));
    assertEquals(Wheel.BR, SpinoutUtil.calcWheelToLock(new Translation2d(1, 0), Rotation2d.fromDegrees(135), SpinoutDirection.CCW, 30));
    assertEquals(Wheel.BL, SpinoutUtil.calcWheelToLock(new Translation2d(1, 0), Rotation2d.fromDegrees(225), SpinoutDirection.CCW, 30));
    assertEquals(Wheel.FL, SpinoutUtil.calcWheelToLock(new Translation2d(1, 0), Rotation2d.fromDegrees(315), SpinoutDirection.CCW, 30));
  }

  @Test
  public void testCalcWheelToLock_DifferentDirections() {
    // Backwards
    assertEquals(Wheel.BL, SpinoutUtil.calcWheelToLock(new Translation2d(-1, 0), Rotation2d.fromDegrees(90), SpinoutDirection.CW, 30));
    assertEquals(Wheel.FR, SpinoutUtil.calcWheelToLock(new Translation2d(-1, 0), Rotation2d.fromDegrees(180), SpinoutDirection.CCW, 30));

    // Left
    assertEquals(Wheel.FL, SpinoutUtil.calcWheelToLock(new Translation2d(0, 1), Rotation2d.fromDegrees(90), SpinoutDirection.CW, 30));
    assertEquals(Wheel.BR, SpinoutUtil.calcWheelToLock(new Translation2d(0, 1), Rotation2d.fromDegrees(180), SpinoutDirection.CCW, 30));

    // Right
    assertEquals(Wheel.BR, SpinoutUtil.calcWheelToLock(new Translation2d(0, -1), Rotation2d.fromDegrees(90), SpinoutDirection.CW, 30));
    assertEquals(Wheel.FL, SpinoutUtil.calcWheelToLock(new Translation2d(0, -1), Rotation2d.fromDegrees(180), SpinoutDirection.CCW, 30));
  }

  @Test
  public void testCalcWheelToLock_CornerSpans() {
    assertEquals(Wheel.FL, SpinoutUtil.calcWheelToLock(new Translation2d(1, 0), Rotation2d.fromDegrees(29), SpinoutDirection.CW, 30));
    assertEquals(Wheel.FR, SpinoutUtil.calcWheelToLock(new Translation2d(1, 0), Rotation2d.fromDegrees(30), SpinoutDirection.CW, 30));
    assertEquals(Wheel.FR, SpinoutUtil.calcWheelToLock(new Translation2d(1, 0), Rotation2d.fromDegrees(29), SpinoutDirection.CW, 45));
  }

  @Test
  public void testCalcWheelToLock_BadCornerSpans() {
    assertEquals(Wheel.FR, SpinoutUtil.calcWheelToLock(new Translation2d(1, 0), Rotation2d.fromDegrees(45), SpinoutDirection.CW, 0));
    assertEquals(Wheel.BR, SpinoutUtil.calcWheelToLock(new Translation2d(1, 0), Rotation2d.fromDegrees(45), SpinoutDirection.CCW, 0));

    assertEquals(Wheel.FR, SpinoutUtil.calcWheelToLock(new Translation2d(1, 0), Rotation2d.fromDegrees(0), SpinoutDirection.CW, 90));
    assertEquals(Wheel.FR, SpinoutUtil.calcWheelToLock(new Translation2d(1, 0), Rotation2d.fromDegrees(0), SpinoutDirection.CCW, 90));

    assertEquals(Wheel.FR, SpinoutUtil.calcWheelToLock(new Translation2d(1, 0), Rotation2d.fromDegrees(0), SpinoutDirection.CW, 91));
    assertEquals(Wheel.FR, SpinoutUtil.calcWheelToLock(new Translation2d(1, 0), Rotation2d.fromDegrees(0), SpinoutDirection.CCW, 91));
  }

  @Test
  public void testCalcWheelToLock_NoInput() {
    assertEquals(Wheel.FL, SpinoutUtil.calcWheelToLock(new Translation2d(0, 0), Rotation2d.fromDegrees(0), SpinoutDirection.CW, 30));
    assertEquals(Wheel.FR, SpinoutUtil.calcWheelToLock(new Translation2d(0, 0), Rotation2d.fromDegrees(0), SpinoutDirection.CCW, 30));
  }

  @Test
  public void testCalcWheelToLock_RegularInputs() {
    assertEquals(Wheel.BL, SpinoutUtil.calcWheelToLock(new Translation2d(-0.357, -0.61), Rotation2d.fromDegrees(156), SpinoutDirection.CW, 30));
    assertEquals(Wheel.FR, SpinoutUtil.calcWheelToLock(new Translation2d(0.19, 0.687), Rotation2d.fromDegrees(-240), SpinoutDirection.CCW, 30));
    assertEquals(Wheel.FL, SpinoutUtil.calcWheelToLock(new Translation2d(0.157, -0.69), Rotation2d.fromDegrees(296), SpinoutDirection.CW, 30));
    assertEquals(Wheel.FR, SpinoutUtil.calcWheelToLock(new Translation2d(0.705, -0.055), Rotation2d.fromDegrees(3), SpinoutDirection.CCW, 30));
  }
}