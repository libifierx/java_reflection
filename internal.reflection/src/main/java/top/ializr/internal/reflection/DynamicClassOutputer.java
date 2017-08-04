package top.ializr.internal.reflection;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

public class DynamicClassOutputer implements ClassFileTransformer {
	
	static String dirpath =  "/dynamicPath";
	
	public static void premain(String agentArgs, Instrumentation inst){
		inst.addTransformer(new DynamicClassOutputer());
	}
	
	@Override
	public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
			ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
		if(!isExists(className)) {
			int lastIndexOf = className.lastIndexOf(".");
			String fileName = className.substring(lastIndexOf+1);
			try {
				exportClassToFile(fileName,classfileBuffer);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return classfileBuffer;
	}
	
	private void exportClassToFile(String fileName, byte[] classfileBuffer) throws IOException {
		File file = new File(dirpath+"/"+fileName);
		if(!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		System.out.println("export:" + file.getAbsolutePath() );
		FileOutputStream fos = new FileOutputStream(dirpath+"/"+fileName+".class");
		fos.write(classfileBuffer);
		fos.flush();
		fos.close();
	}

	private boolean isExists(String className) {
		return this.getClass().getClassLoader().getResourceAsStream(className+".class") != null;
	}

}
