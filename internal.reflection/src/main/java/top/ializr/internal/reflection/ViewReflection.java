package top.ializr.internal.reflection;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ViewReflection {
	
	public void testPrint(String a){
		System.out.println(a);
	}
	public static void main(String[] args) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		ViewReflection vf = new ViewReflection();
		vf.testPrint("主动窥探");
		
		Method m = ViewReflection.class.getDeclaredMethod("testPrint",String.class);
		for (int i = 0; i < 100; i++) {
			m.invoke(vf, "反射窥探");
		}
	}
}
