import com.intellij.core.JavaCoreApplicationEnvironment;
import com.intellij.core.JavaCoreProjectEnvironment;
import com.intellij.ide.highlighter.JavaFileType;
import com.intellij.lang.FileASTNode;
import com.intellij.mock.MockProject;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiFileFactory;
import com.intellij.psi.PsiJavaFile;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class Main {


  public static void main(String[] args) throws IOException {
    PsiFileFactory psiFileFactory = createPsiFactory();
    final ClassLoader classLoader = Main.class.getClassLoader();
    final URL fileResource = classLoader.getResource("TestClass.java");
    if(fileResource != null) {
      File file = new File(fileResource.getFile());
      String javaSource = FileUtil.loadFile(file);
      FileASTNode node = parseJavaSource(javaSource, psiFileFactory);
      System.out.println(node);
    }
  }

  private static PsiFileFactory createPsiFactory() {
    MockProject mockProject = createProject();
    return PsiFileFactory.getInstance(mockProject);
  }

  private static FileASTNode parseJavaSource(String JAVA_SOURCE, PsiFileFactory psiFileFactory) {
    PsiFile psiFile = psiFileFactory.createFileFromText("__dummy_file__.java", JavaFileType.INSTANCE, JAVA_SOURCE);
    PsiJavaFile psiJavaFile = (PsiJavaFile)psiFile;
    return psiJavaFile.getNode();
  }

  private static MockProject createProject() {
    JavaCoreProjectEnvironment environment = new JavaCoreProjectEnvironment(new Disposable() {
      @Override
      public void dispose() {
      }
    }, new JavaCoreApplicationEnvironment(new Disposable() {
      @Override
      public void dispose() {
      }
    }));

    return environment.getProject();
  }

}