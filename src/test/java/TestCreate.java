import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import org.csnowfox.maven.plugin.pdm2mybatis.Pdm2MybatisJavaCode;

/**
 * 本地测试类
 */
public class TestCreate {

	public static void main(String[] args){

		String pathdao = "D:/temp/api/";
		String pathpack = "com.csnowfox.hj.acp.api";
		String projectname = "hj-acp";
		String pathpdm = "D:\\workspace\\pdm2mybatis\\src\\test\\java\\fund.pdm";
		String tables = "FUND_CALENDAR";
		String pathsql = "D:/temp";
		String namesql = "MYTest.sql";
		String interfaceName = "com.csnowfox.hj.acp.api.service.SqlMapper";
		
		File pathFile = new File(pathpdm);
		if(pathFile.isFile()){
			Pdm2MybatisJavaCode.createFiles(pathdao, pathsql, namesql, projectname, pathpack, pathpdm, tables, interfaceName);
		}else{
			try{
				Map<String,String> userMap = new HashMap<String, String>();
				StringTokenizer fileTokenizer = new StringTokenizer(tables, "|");
				while(fileTokenizer.hasMoreTokens()){
					String fileToken = fileTokenizer.nextToken().trim();
					StringTokenizer userTokenizer = new StringTokenizer(fileToken, ":");
					String userToken = userTokenizer.nextToken().trim();
					String tableListString = userTokenizer.nextToken().trim();
					userMap.put(userToken, tableListString);
				}
				File[] subFile = pathFile.listFiles();
				for(File tmpFile : subFile){
					String tmpFileName = tmpFile.getName();
					int dotIndex = tmpFileName.lastIndexOf(".");
					tmpFileName = tmpFileName.substring(0, dotIndex);
					if(userMap.containsKey(tmpFileName)){
						String newTables = userMap.get(tmpFileName);
						Pdm2MybatisJavaCode.createFiles(pathdao, pathsql, namesql, projectname, pathpack.toLowerCase(), tmpFile.getAbsolutePath(), newTables, interfaceName);
					}
				}
			}catch(Exception ex){
				System.out.println(ex);
			}
		}
		
	}
}
