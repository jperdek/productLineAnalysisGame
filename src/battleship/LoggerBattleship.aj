package battleship;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.aspectj.lang.Signature;

public aspect LoggerBattleship {
	private Logger _logger = Logger.getLogger("trace");
	
	pointcut traceMethods(): execution(* *.*(..)) && !within(LoggerBattleship);
	
	before(): traceMethods() { // better is use before() and after() then around - around needs more resources and last longer
		Signature signature = thisJoinPointStaticPart.getSignature();
		_logger.logp(Level.INFO, signature.getDeclaringType().getName(), signature.getName(), "Entering");
	}
}
