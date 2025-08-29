package controller;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import javafx.scene.control.ScrollPane;
import javafx.application.Platform;
import java.math.BigDecimal;
import java.math.RoundingMode;

import handler.calculation;

import javafx.scene.input.KeyCode;


public class appController 
{

	@FXML
    private Button absButton;

    @FXML
    private Button acosButton;

    @FXML
    private Button acotButton;

    @FXML
    private Button ansButton;

    @FXML
    private Button asinButton;

    @FXML
    private Button atanButton;

    @FXML
    private Button closebracketButton;

    @FXML
    private Button clrButton;

    @FXML
    private Button cosButton;

    @FXML
    private Button cotButton;

    @FXML
    private Button delButton;

    @FXML
    private Button dotButton;

    @FXML
    private Button e_exButton;

    @FXML
    private Button eightButton;

    @FXML
    private Button enumButton;

    @FXML
    private Button equalButton;

    @FXML
    private Button exponentialButton;

    @FXML
    private Button factorialButton;

    @FXML
    private Button fiveButton;

    @FXML
    private Button fourButton;

    @FXML
    private Button fractionButton;

    @FXML
    private Button fraction_showButton;

    @FXML
    private TextField inputText;

    @FXML
    private Button logButton;

    @FXML
    private Button minusButton;

    @FXML
    private Button moduloButton;

    @FXML
    private Button multiplyButton;

    @FXML
    private Button nineButton;

    @FXML
    private Button oneButton;

    @FXML
    private Button openbracketButton;

    @FXML
    private Button commaButton;

    @FXML
    private Button piButton;

    @FXML
    private Button plusButton;

    @FXML
    private TextField resultsText;

    @FXML
    private Button rootButton;

    @FXML
    private Button sevenButton;

    @FXML
    private Button sinButton;

    @FXML
    private Button sixButton;

    @FXML
    private Button tanButton;

    @FXML
    private Button threeButton;

    @FXML
    private Button twoButton;

    @FXML
    private Button zeroButton;
    
    @FXML private ScrollPane topScroll;
    @FXML private AnchorPane topContent;
    
    private final Text measurer = new Text();
  
    
    // Engine để tính toán
    private final calculation engine = new calculation(true);

    @FXML
    public void initialize() 
    {
        // KQ
    	resultsText.addEventFilter(javafx.scene.input.KeyEvent.KEY_TYPED, e -> e.consume());
    	resultsText.addEventFilter(javafx.scene.input.KeyEvent.KEY_PRESSED, e -> {
    	    switch (e.getCode()) {
    	        case BACK_SPACE:
    	        case DELETE:
    	        case ENTER:
    	        case TAB:
    	            e.consume();
    	            break;
    	        default:
    	            // Cho phép copy và select all
    	            if (e.isControlDown()) {
    	                switch (e.getCode()) {
    	                    case C: case A: return;
    	                }
    	            }
    	            // Còn lại thì không được =))))
    	            e.consume();
    	    }
    	});
        // input: cho gõ số/chữ/π/toán tử
        inputText.addEventFilter(
        		KeyEvent.KEY_TYPED, e -> {
        			if (!e.getCharacter().matches("[0-9A-Za-z\\-\\+\\^\\*/x×÷\\.,()\\sπ]")) e.consume();
        			}
        		);
        inputText.setOnAction(e -> equalButton.fire());

        // Khởi tạo
        zeroButton.setOnAction(e -> append("0"));
        oneButton.setOnAction(e -> append("1"));
        twoButton.setOnAction(e -> append("2"));
        threeButton.setOnAction(e -> append("3"));
        fourButton.setOnAction(e -> append("4"));
        fiveButton.setOnAction(e -> append("5"));
        sixButton.setOnAction(e -> append("6"));
        sevenButton.setOnAction(e -> append("7"));
        eightButton.setOnAction(e -> append("8"));
        nineButton.setOnAction(e -> append("9"));

        openbracketButton.setOnAction(e -> append("("));
        closebracketButton.setOnAction(e -> append(")"));
        dotButton.setOnAction(e -> append("."));
        commaButton.setOnAction(e -> append(","));

        piButton.setOnAction(e -> append("π"));
        enumButton.setOnAction(e -> append("e"));
        ansButton.setOnAction(e -> append("Ans"));

        sinButton.setOnAction(e -> append("sin("));
        cosButton.setOnAction(e -> append("cos("));
        tanButton.setOnAction(e -> append("tan("));
        cotButton.setOnAction(e -> append("cot("));
        asinButton.setOnAction(e -> append("asin("));
        acosButton.setOnAction(e -> append("acos("));
        atanButton.setOnAction(e -> append("atan("));
        acotButton.setOnAction(e -> append("acot("));
        absButton.setOnAction(e -> append("Abs("));
        logButton.setOnAction(e -> append("log("));
        e_exButton.setOnAction(e -> append("e^("));
        fraction_showButton.setOnAction(e -> append("frac("));
        rootButton.setOnAction(e -> append("root("));
        factorialButton.setOnAction(e -> append("!"));
        moduloButton.setOnAction(e -> append(" mod "));
        exponentialButton.setOnAction(e -> append("^"));

        plusButton.setOnAction(e -> append(" + "));
        minusButton.setOnAction(e -> append(" - "));
        multiplyButton.setOnAction(e -> append(" × "));
        fractionButton.setOnAction(e -> append(" / "));

        equalButton.setOnAction(e -> onEqual());
        delButton.setOnAction(e -> deleteBlock());
        clrButton.setOnAction(e -> clearAll());

        clearAll();
        
        
        measurer.setFont(inputText.getFont());

        ChangeListener<Object> updater = (obs, o, n) -> updateWidthsAndScroll();

        inputText.textProperty().addListener(updater);
        inputText.fontProperty().addListener(updater);
        topScroll.viewportBoundsProperty().addListener(updater);

        // chạy lần đầu
        updateWidthsAndScroll();
    }
    
    
    private void updateWidthsAndScroll() {
        // luôn đồng bộ font (CSS có thể áp sau initialize)
        measurer.setFont(inputText.getFont());

        double inputW   = measureTextWidth(inputText.getText());
        double resultsW = measureTextWidth(resultsText.getText());
        double textW    = Math.max(inputW, resultsW);

        double padding   = 28; // 14 trái + 14 phải
        double viewportW = topScroll.getViewportBounds().getWidth();
        double desiredContentW = Math.max(viewportW, textW + padding);

        topContent.setPrefWidth(desiredContentW);
        inputText.setPrefWidth(desiredContentW - padding);
        resultsText.setPrefWidth(desiredContentW - padding);

        boolean caretAtEnd = inputText.getCaretPosition() == inputText.getLength();
        if (caretAtEnd) {
            Platform.runLater(() -> {
                // kiểm tra lại lúc thực thi (phòng trường hợp bạn vừa kéo trái xong)
                if (inputText.getCaretPosition() == inputText.getLength()) {
                    topScroll.setHvalue(1.0);
                }
            });
        }
    }

    private double measureTextWidth(String s) {
        if (s == null || s.isEmpty()) s = " ";
        measurer.setText(s);
        return Math.ceil(measurer.getLayoutBounds().getWidth());
    }

    /* ===== helpers ===== */
    private void append(String s) {
        inputText.appendText(s);
        inputText.requestFocus();
        inputText.positionCaret(inputText.getText().length());
    }

    private void clearAll() {
        inputText.clear();
        resultsText.clear();
    }

    private String format(double v) {
        if (Double.isNaN(v) || Double.isInfinite(v)) return "Error";
        BigDecimal bd = BigDecimal.valueOf(v).setScale(12, RoundingMode.HALF_UP).stripTrailingZeros();
        return bd.toPlainString();
    }

    private void deleteBlock() {
        String s = inputText.getText();
        if (s == null || s.isBlank()) return;

        int end = s.length() - 1;
        while (end >= 0 && Character.isWhitespace(s.charAt(end))) end--;
        if (end < 0) { inputText.clear(); return; }

        int start = end;
        char c = s.charAt(end);

        if (Character.isDigit(c) || c == '.') {
            while (start >= 0 && (Character.isDigit(s.charAt(start)) || s.charAt(start) == '.')) start--;
            start++;
        }
        else if (c == ')') {
            start = end;
        }

        else if (c == '(') {
            while (start - 1 >= 0) {
                char p = s.charAt(start - 1);
                if (Character.isLetter(p) || p == '^') start--;
                else break;
            }
        }
        // chữ cái (tên hàm/hằng), gồm "Ans"
        else if (Character.isLetter(c)) {
            while (start - 1 >= 0 && Character.isLetter(s.charAt(start - 1))) start--;
            // nếu là "mod" thì ăn luôn khoảng trắng 2 bên
            String word = s.substring(start, end + 1);
            if (word.equals("mod")) {
                while (start - 1 >= 0 && s.charAt(start - 1) == ' ') start--;
                int r = end + 1;
                while (r < s.length() && s.charAt(r) == ' ') r++;
                inputText.setText(s.substring(0, start) + s.substring(r));
                return;
            }
        }
        // ký tự π
        else if (c == 'π') {
            start = end; // xoá một ký tự
        }
        // toán tử + - * / ^ (kể cả ×, ÷)
        else if ("+-^*/".indexOf(c) >= 0 || c == '×' || c == '÷') {
            // xoá cả khoảng trắng liền kề
            while (start - 1 >= 0 && s.charAt(start - 1) == ' ') start--;
            int r = end + 1;
            while (r < s.length() && s.charAt(r) == ' ') r++;
            inputText.setText(s.substring(0, start) + s.substring(r));
            return;
        } else {
            // ký tự khác -> xoá 1
            start = end;
        }

        inputText.setText(s.substring(0, start) + s.substring(end + 1));
    }

    // Dấu '='
    private void onEqual() {
        try {
            String original = inputText.getText().trim();
            double val = evaluate(original);
            // set Ans
            engine.add(val, 0);
            resultsText.setText(original + " = " + format(val));
            inputText.setText(format(val));
        } catch (Exception ex) {
            resultsText.setText("Error: " + ex.getMessage());
        }
    }

    // Ưu tiên mức độ
    private double evaluate(String expr) {
        if (expr == null) return 0.0;
        String s = expr.replaceAll("\\s+", "").replace('×', '*').replace('x', '*').replace('÷', '/');
        return new Parser(s).parse();
    }

    private final class Parser {
        private final String s;
        private int i = 0;

        Parser(String s) { this.s = s; }

        double parse() {
            double v = parseAddSub();
            if (i != s.length()) throw new IllegalArgumentException("Unexpected: " + s.substring(i));
            return v;
        }

        // + -
        double parseAddSub() {
            double v = parseMulDivMod();
            while (true) {
                if (match('+')) v += parseMulDivMod();
                else if (match('-')) v -= parseMulDivMod();
                else return v;
            }
        }

        double parseMulDivMod() {
            double v = parsePow();
            while (true) {
                if (match('*')) v = engine.mul(v, parsePow());
                else if (match('/')) v = engine.div(v, parsePow());
                else if (matchWord("mod")) v = engine.mod(v, parsePow());
                else if (beginsAtom()) v = engine.mul(v, parsePow()); // nhân ngầm
                else return v;
            }
        }

        double parsePow() {
            double base = parsePostfix();
            if (match('^')) {
                double exp = parsePow();
                return engine.pow(base, exp);
            }
            return base;
        }

        // hậu tố !
        double parsePostfix() {
            double v = parseUnary();
            while (match('!')) {
                int n = (int) Math.round(v);
                if (n < 0 || Math.abs(v - n) > 1e-9)
                    throw new IllegalArgumentException("n! requires non-negative integer");
                if (n > 170) throw new IllegalArgumentException("n! too large (n<=170)");
                java.math.BigInteger big = engine.factorial(n);
                v = new java.math.BigDecimal(big).doubleValue();
            }
            return v;
        }

        // dấu âm, hằng, số, ngoặc, hàm
        double parseUnary() {
            if (match('+')) return parseUnary();
            if (match('-')) return -parseUnary();

            if (match('π')) return Math.PI;                      // π
            if (matchWord("e"))  return Math.E;                  // e
            if (matchWordIgnoreCase("Ans")) return engine.getAns();

            if (peekIsLetter() || (peek()=='e' && peekNext()=='^')) {
                String name = readName(); // chữ + '^' (để nhận e^)
                if (match('(')) {
                    String inside = readUntilBalanced(')');
                    switch (name) {
                        case "sin":  return engine.sin(evaluate(inside));
                        case "cos":  return engine.cos(evaluate(inside));
                        case "tan":  return engine.tan(evaluate(inside));
                        case "cot":  return engine.cot(evaluate(inside));
                        case "asin": return engine.asin(evaluate(inside));
                        case "acos": return engine.acos(evaluate(inside));
                        case "atan": return engine.atan(evaluate(inside));
                        case "acot": return engine.acot(evaluate(inside));
                        case "Abs":  return engine.abs(evaluate(inside));
                        case "log":  return engine.log10(evaluate(inside));
                        case "e^":   return engine.exp(evaluate(inside));
                        case "frac": {
                            String[] ab = split2Args(inside);
                            return engine.div(evaluate(ab[0]), evaluate(ab[1]));
                        }
                        case "root": {
                            String[] na = split2Args(inside);
                            return engine.nthRoot(evaluate(na[0]), evaluate(na[1]));
                        }
                        default: throw new IllegalArgumentException("Unknown func: " + name);
                    }
                } else throw new IllegalArgumentException("Missing '(' after " + name);
            }

            if (match('(')) {
                String inside = readUntilBalanced(')');
                return evaluate(inside);
            }

            return readNumber();
        }

        private char peek() { return i < s.length() ? s.charAt(i) : '\0'; }
        private char peekNext() { return (i+1) < s.length() ? s.charAt(i+1) : '\0'; }
        private boolean match(char c) { if (peek()==c) { i++; return true; } return false; }
        private boolean matchWord(String w) { if (s.startsWith(w, i)) { i += w.length(); return true; } return false; }
        private boolean matchWordIgnoreCase(String w) {
            if (s.regionMatches(true, i, w, 0, w.length())) { i += w.length(); return true; }
            return false;
        }
        private boolean peekIsLetter() { char c = peek(); return (c>='A'&&c<='Z') || (c>='a'&&c<='z'); }

        private boolean beginsAtom() {
            char c = peek();
            if (c=='(' || c=='π' || Character.isDigit(c) || c=='.') return true;
            if (peekIsLetter()) return true;
            if (s.startsWith("Ans", i)) return true;
            return false;
        }

        private String readName() {
            int start = i;
            while (i < s.length()) {
                char c = s.charAt(i);
                if (Character.isLetter(c) || c=='^') i++; else break;
            }
            return s.substring(start, i);
        }

        private String readUntilBalanced(char closing) {
            int depth = 1;
            int start = i;
            while (i < s.length()) {
                char c = s.charAt(i++);
                if (c == '(') depth++;
                else if (c == ')') { depth--; if (depth == 0) break; }
            }
            if (depth != 0) throw new IllegalArgumentException("Missing ')'");
            return s.substring(start, i-1);
        }

        private String[] split2Args(String inside) {
            int depth = 0, cut = -1;
            for (int k = 0; k < inside.length(); k++) {
                char c = inside.charAt(k);
                if (c == '(') depth++;
                else if (c == ')') depth--;
                else if (c == ',' && depth == 0) { cut = k; break; }
            }
            if (cut < 0) throw new IllegalArgumentException("Need two args");
            return new String[]{ inside.substring(0, cut), inside.substring(cut + 1) };
        }

        private double readNumber() {
            int start = i;
            boolean hasDot = false;
            while (i < s.length()) {
                char c = s.charAt(i);
                if (Character.isDigit(c)) i++;
                else if (c == '.' && !hasDot) { hasDot = true; i++; }
                else break;
            }
            if (start == i) throw new IllegalArgumentException("Number expected at: " + s.substring(i));
            return Double.parseDouble(s.substring(start, i));
        }
    }
}
