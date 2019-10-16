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

public class StringLiteralChangerAstVisitor extends ASTVisitor {
/*
    @SuppressWarnings("unchecked")
    public boolean visit(VariableDeclarationFragment node) {
        return true;
    }

    public boolean visit(SimpleName node) {
        return true;
    }
*/

    @SuppressWarnings("unchecked")
    @Override
    public boolean visit(StringLiteral node) {

        ASTRewrite rewrite = ASTRewrite.create(node.getAST());
        // Second type
        //node.setStructuralProperty(SimpleType.VALUE, rewrite
        //      .getAST().newSimpleValue("<UNK>"));
        try {
            rewrite.replace(node, rewrite.getAST().newValue("<UNK>"), null);
        } catch (MalformedTreeException e) {
            e.printStackTrace();
        } catch (org.eclipse.jface.text.BadLocationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return true;
    }
}

public class StringLiteralChangerAstVisitorWithCache extends StringLiteralChangerAstVisitor {

    @Override
    public boolean visit(StringLiteral node) {
        try {
            ListRewrite lrw = rewriter.getListRewrite(node, CompilationUnit.CHANGE_VALUE);
            lrw.change("<UNK>", null);
            TextEdit edits = rewriter.rewriteAST(null);
            edits.apply();
            edits.save();
        } catch (MalformedTreeException e) {
            e.printStackTrace();
        } catch (org.eclipse.jface.text.BadLocationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return true;
    }
}
