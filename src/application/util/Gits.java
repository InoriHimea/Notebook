package application.util;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.LogCommand;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;

import application.Constants;

public class Gits {

	public interface Callback {
		void success();
		void error(Exception e);
	}

	public static void push(Callback callback){
		try {
			// URI uri = Gits.class.getResource("/").toURI();
			String projectDir = System.getProperty("user.dir");
			// =============== ����  ===============
			File encryptdb = new File(projectDir,"encrypt.db");
			if(!encryptdb.exists()){
				encryptdb.createNewFile();
			}

			byte[] datas = IOUtils.toByteArray(new FileInputStream("notebook.db"));

			String secret = Preferences.get(Constants.CONFIG_SECRET);
			if("".equals(secret)){
				throw new IllegalArgumentException("δ���ü�����Կ");
			}

			byte[] encryptDatas = PBECoder.encript(datas, secret, "13572468".getBytes());
			System.out.println(encryptDatas.length);
			FileUtils.writeByteArrayToFile(encryptdb, encryptDatas);

			// =============== ����  ===============
			String gitResitory = projectDir + File.separator + ".git";
			Git git = Git.open(new File(gitResitory));

			Repository repository = git.getRepository();
			System.out.println(repository.getBranch());

			// git log
			LogCommand logCommand = git.log();
            Iterator<RevCommit> iterator= logCommand.call().iterator();
            while (iterator.hasNext()) {
                RevCommit revCommit = iterator.next();
                // System.out.println(new String(revCommit.getRawBuffer()));
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

            callback.success();

		} catch (Exception e) {
			callback.error(e);
		}
	}
}
