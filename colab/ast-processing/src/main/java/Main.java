/*

⠰⡿⠿⠛⠛⠻⠿⣷ 
⠀⠀⠀⠀⠀⠀⣀⣄⡀⠀⠀⠀⠀⢀⣀⣀⣤⣄⣀⡀ 
⠀⠀⠀⠀⠀⢸⣿⣿⣷⠀⠀⠀⠀⠛⠛⣿⣿⣿⡛⠿⠷ 
⠀⠀⠀⠀⠀⠘⠿⠿⠋⠀⠀⠀⠀⠀⠀⣿⣿⣿⠇ 
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⠉⠁ 

⠀⠀⠀⠀⣿⣷⣄⠀⢶⣶⣷⣶⣶⣤⣀ 
⠀⠀⠀⠀⣿⣿⣿⠀⠀⠀⠀⠀⠈⠙⠻⠗ 
⠀⠀⠀⣰⣿⣿⣿⠀⠀⠀⠀⢀⣀⣠⣤⣴⣶⡄ 
⠀⣠⣾⣿⣿⣿⣥⣶⣶⣿⣿⣿⣿⣿⠿⠿⠛⠃ 
⢰⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡄ 
⢸⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡁ 
⠈⢿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠁ 
⠀⠀⠛⢿⣿⣿⣿⣿⣿⣿⡿⠟ 
⠀⠀⠀⠀⠀⠉⠉⠉

Оставь надежду, всяк сюда входящий
*/

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import processing.TreeBinarizator;

public class Main {

    public static void main(String[] args) {
        ASTParser parser = ASTParser.newParser(AST.JLS8);
        parser.setResolveBindings(true);
        parser.setStatementsRecovery(true);
        parser.setBindingsRecovery(true);
        parser.setKind(ASTParser.K_COMPILATION_UNIT);

        File resource = new File("models/java_for_lstm");
        java.nio.file.Path sourcePath = Paths.get(resource.toURI());
        String sourceString = new String(Files.readAllBytes(sourcePath));
        char[] source = sourceString.toCharArray();
        parser.setSource(source);
        parser.setUnitName(sourcePath.toAbsolutePath().toString());

        CompilationUnit astRoot = (CompilationUnit) parser.createAST(new NullProgressMonitor());
        ast.recordModifications();

        ProcessAstLiterals(astRoot);
        Binarize(astRoot);
        Serializer.Serialize(astRoot);
    }

    public static void ProcessAstLiterals(CompilationUnit unit) {
        unit.accept(new StringLiteralChangerAstVisitorWithCache());
    }

    public static void Binarize(CompilationUnit unit) {
        unit.accept(new TreeBinarizatorAstVisitor());
    }
}
