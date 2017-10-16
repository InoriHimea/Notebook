package application.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.LogCommand;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;

public class Gits {

	public static void push(){
		try {
			// URI uri = Gits.class.getResource("/").toURI();
			String projectDir = System.getProperty("user.dir");
			// ����
			File db = new File(projectDir,"encrypt.db");
			if(!db.exists()){
				db.createNewFile();
			}

			String gitResitory = projectDir + File.separator + ".git";
			System.out.println(gitResitory);
			Git git = Git.open(new File(gitResitory));

			Repository repository = git.getRepository();
			System.out.println(repository.getBranch());

			// git log
			LogCommand logCommand = git.log();
            Iterator<RevCommit> iterator= logCommand.call().iterator();
            while (iterator.hasNext()) {
                RevCommit revCommit = iterator.next();
                System.out.println(new String(revCommit.getRawBuffer()));
            }

            /******************************************************************************
             * ------------------------- git add �÷� -------------------------
             * ��ʾ��<path>������tracked�ļ��б��޸Ĺ�����ɾ���ļ�������untracted���ļ���Ϣ��ӵ������⡣
             * addFilepattern(". -A").call()
             * addFilepattern("README").call()
             * --------------------------------------------------------------
             *****************************************************************************/

            git.add().addFilepattern("encrypt.db").call();
            // git commit
            SimpleDateFormat sf = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
            StringBuffer message = new StringBuffer();
            message.append("=======");
            message.append(" :smile: ");
            message.append("update Notebook.db");
            message.append(" on ");
            message.append(sf.format(new Date()));
            message.append(" =======");
            git.commit().setAll(true).setAuthor("deeper", "kaiyuan@qq.com").setMessage(message.toString()).call();

	        // https ��ʽ�ύ
            // git.push().setCredentialsProvider(new UsernamePasswordCredentialsProvider(username,password)).call();

            // ssh��ʽ�ύ
            AllowHostsCredentialsProvider allowHosts = new AllowHostsCredentialsProvider();
            git.push().setCredentialsProvider(allowHosts).call();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
}
