package handler;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;


public class calculation {

	private boolean degrees = true;   // true = DEG, false = RAD
    private double ans = 0.0;         // nút Ans

    public calculation() {}
    public calculation(boolean degrees) { this.degrees = degrees; }

    public void setDegrees(boolean degrees) 
    { 
    	this.degrees = degrees; 
    }
    public boolean isDegrees() 
    { 
    	return degrees; 
    }
    public double getAns() 
    { 
    	return ans; 
    }

    /* ================== Helpers ================== */
    private double setAns(double v) { ans = v; return v; }
    private double toRad(double x)  { return degrees ? Math.toRadians(x) : x; }
    private double fromRad(double r){ return degrees ? Math.toDegrees(r) : r; }

    /* ================== Cơ bản ================== */
    // Phép cộng
    public double add(double a, double b) 
    {
    	return setAns(a + b);
    }
    
    // Phép trừ
    public double sub(double a, double b) 
    { 
    	return setAns(a - b);
    }
    
    // Phép nhân
    public double mul(double a, double b)
    {
    	return setAns(a * b); 
    }
    
    // Phép chia
    public double div(double a, double b) 
    {
        if (b == 0.0) throw new ArithmeticException("Division by zero");
        return setAns(a / b);
    }
    
    // Lấy mũ
    public double pow(double a, double b) 
    {
        return setAns(Math.pow(a, b));
    }

    // Lấy căn
    public double nthRoot(double n, double a) 
    {
        if (n == 0.0) throw new IllegalArgumentException("n must not be 0");
        boolean nIsInteger = Math.floor(n) == n;
        boolean nIsOddInt = nIsInteger && ((long)Math.abs(n)) % 2L == 1L;
        if (a < 0 && !nIsOddInt)
        {
            throw new IllegalArgumentException("Even root of negative number");
        }
        double result = Math.copySign(Math.pow(Math.abs(a), 1.0 / n), a);
        return setAns(result);
    }

    // Nút phần trăm
    public double percent(double x) { return setAns(x / 100.0); }

//    /** Làm tròn (≈) tới 'decimals' chữ số thập phân. */
//    public double approx(double x, int decimals) {
//        BigDecimal bd = BigDecimal.valueOf(x)
//                .setScale(decimals, RoundingMode.HALF_UP);
//        return setAns(bd.doubleValue());
//    }

    // Chia lấy dư
    public double mod(double a, double b) {
        if (b == 0.0) throw new ArithmeticException("Modulo by zero");
        return setAns(a % b);
    }
    
    // Trị tuyệt đối
    public double abs(double x)
    { 
    	return setAns(Math.abs(x)); 
    }

    // Hằng số
    public double pi() { return setAns(Math.PI); }
    public double e()  { return setAns(Math.E);  }

    // e^x
    public double exp(double x) 
    { 
    	return setAns(Math.exp(x)); 
    }
    
    // Các hàm log
    public double log10(double x) 
    {
        if (x <= 0.0) throw new IllegalArgumentException("log10 domain x>0");
        return setAns(Math.log10(x));
    }
    public double ln(double x) 
    {
        if (x <= 0.0) throw new IllegalArgumentException("ln domain x>0");
        return setAns(Math.log(x));
    }
    
    public double logBase(double a, double b) 
    {
        if (a <= 0.0 || a == 1.0) throw new IllegalArgumentException("Base a must be >0 and !=1");
        if (b <= 0.0) throw new IllegalArgumentException("b must be > 0");
        return setAns(Math.log(b) / Math.log(a));
    }

    // Giai thừa
    public BigInteger factorial(int n) 
    {
        if (n < 0) throw new IllegalArgumentException("n must be >= 0");
        BigInteger r = BigInteger.ONE;
        for (int i = 2; i <= n; i++) r = r.multiply(BigInteger.valueOf(i));
        return r;
    }

    // Lượng giác
    public double sin(double x) 
    { 
    	return setAns(Math.sin(toRad(x))); 
    }
    public double cos(double x) 
    { 
    	return setAns(Math.cos(toRad(x))); 
    }
    public double tan(double x) 
    {
        double r = Math.tan(toRad(x));
        if (Double.isInfinite(r) || Double.isNaN(r))
            throw new ArithmeticException("tan undefined at this angle");
        return setAns(r);
    }

    public double cot(double x) 
    {
        double t = Math.tan(toRad(x));
        if (t == 0.0) throw new ArithmeticException("cot undefined at this angle");
        return setAns(1.0 / t);
    }

    // Lượng giác ngược
    public double asin(double y) 
    {
        if (y < -1.0 || y > 1.0) throw new IllegalArgumentException("asin domain [-1,1]");
        return setAns(fromRad(Math.asin(y)));
    }
    
    public double acos(double y) 
    {
        if (y < -1.0 || y > 1.0) throw new IllegalArgumentException("acos domain [-1,1]");
        return setAns(fromRad(Math.acos(y)));
    }
    
    public double atan(double x) 
    { 
    	return setAns(fromRad(Math.atan(x))); 
    }

    public double acot(double x) 
    {
        double r = (degrees ? Math.PI/2 : (Math.PI/2)) - Math.atan(x);
        return setAns(degrees ? Math.toDegrees(r) : r);
    }

}
