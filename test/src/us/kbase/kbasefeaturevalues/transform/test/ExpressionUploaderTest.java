package us.kbase.kbasefeaturevalues.transform.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import junit.framework.Assert;

import org.ini4j.Ini;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import us.kbase.auth.AuthConfig;
import us.kbase.auth.AuthToken;
import us.kbase.auth.ConfigurableAuthService;
import us.kbase.common.service.UObject;
import us.kbase.common.utils.ProcessHelper;
import us.kbase.kbasefeaturevalues.ExpressionMatrix;
import us.kbase.kbasefeaturevalues.transform.ExpressionUploader;
import us.kbase.workspace.CreateWorkspaceParams;
import us.kbase.workspace.ObjectSaveData;
import us.kbase.workspace.SaveObjectsParams;
import us.kbase.workspace.WorkspaceClient;
import us.kbase.workspace.WorkspaceIdentity;

public class ExpressionUploaderTest {
    private static AuthToken token = null;
    private static File workDir = null;
    private static String wsUrl = null;
    private static String testWsName = null;

    @BeforeClass
    public static void beforeClass() throws Exception {
        workDir = prepareWorkDir("uploader");
        String configFilePath = System.getenv("KB_DEPLOYMENT_CONFIG");
        File deploy = new File(configFilePath);
        Ini ini = new Ini(deploy);
        Map<String, String> config = ini.get("KBaseFeatureValues");
        // Token validation
        String authUrl = config.get("auth-service-url");
        String authUrlInsecure = config.get("auth-service-url-allow-insecure");
        ConfigurableAuthService authService = new ConfigurableAuthService(
                new AuthConfig().withKBaseAuthServerURL(new URL(authUrl))
                .withAllowInsecureURLs("true".equals(authUrlInsecure)));
        token = authService.validateToken(System.getenv("KB_AUTH_TOKEN"));
        // Reading URLs from config
        wsUrl = config.get("ws.url");
        /// Temporary workspace
        String machineName = java.net.InetAddress.getLocalHost().getHostName();
        machineName = machineName == null ? "nowhere" : machineName.toLowerCase().replaceAll("[^\\dA-Za-z_]|\\s", "_");
        long suf = System.currentTimeMillis();
        WorkspaceClient wscl = getWsClient();
        Exception error = null;
        for (int i = 0; i < 5; i++) {
            testWsName = "test_feature_values_" + machineName + "_" + suf;
            try {
                wscl.createWorkspace(new CreateWorkspaceParams().withWorkspace(testWsName));
                error = null;
                break;
            } catch (Exception ex) {
                System.err.println(ex.getMessage());
                error = ex;
            }
        }
        if (error != null)
            throw error;
    }

    private static String searchForSubstring(String[] items, String part) {
        for (String item : items)
            if (item.contains(part))
                return item;
        throw new IllegalStateException("[" + part + "] substring is not found");
    }

    @AfterClass
    public static void afterClass() throws Exception {
        try {
            if (testWsName != null) {
                getWsClient().deleteWorkspace(new WorkspaceIdentity().withWorkspace(testWsName));
                System.out.println("Test workspace [" + testWsName + "] was deleted");
            }
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testUploader1() throws Exception {
        WorkspaceClient wscl = getWsClient();
        String contigsetObjName = "Desulfovibrio_vulgaris_Hildenborough.contigset";
        String genomeObjName = "Desulfovibrio_vulgaris_Hildenborough.genome";
        String exprObjName = "Desulfovibrio_vulgaris_Hildenborough.expression";
        File inputDir = new File("test/data/upload1");
        Map<String, Object> contigsetData = new LinkedHashMap<String, Object>();
        contigsetData.put("contigs", new ArrayList<Object>());
        contigsetData.put("id", "1945.contigset");
        contigsetData.put("md5", "md5");
        contigsetData.put("name", "1945");
        contigsetData.put("source", "User uploaded data");
        contigsetData.put("source_id", "noid");
        contigsetData.put("type", "Organism");
        wscl.saveObjects(new SaveObjectsParams().withWorkspace(testWsName).withObjects(Arrays.asList(
                new ObjectSaveData().withName(contigsetObjName).withType("KBaseGenomes.ContigSet")
                .withData(new UObject(contigsetData)))));
        Map<String, Object> genomeData = UObject.getMapper().readValue(new File(inputDir,
                "Desulfovibrio_vulgaris_Hildenborough_reduced_genome.json"), Map.class);
        genomeData.put("contigset_ref", testWsName + "/" + contigsetObjName);
        wscl.saveObjects(new SaveObjectsParams().withWorkspace(testWsName).withObjects(Arrays.asList(
                new ObjectSaveData().withName(genomeObjName).withType("KBaseGenomes.Genome")
                .withData(new UObject(genomeData)))));
        File exprTempFile = new File(workDir, "expression_output1.json");
        ExpressionUploader.main(new String[] { 
                "--workspace_name", testWsName, 
                "--genome_object_name", genomeObjName, 
                "--input_directory", inputDir.getAbsolutePath(), 
                "--fill_missing_values",
                "--working_directory", exprTempFile.getParentFile().getAbsolutePath(), 
                "--output_file_name", exprTempFile.getName(),
                "--format_type", "MO"});
        ExpressionMatrix data = UObject.getMapper().readValue(exprTempFile, ExpressionMatrix.class);
        wscl.saveObjects(new SaveObjectsParams().withWorkspace(testWsName).withObjects(Arrays.asList(
                new ObjectSaveData().withName(exprObjName).withType("KBaseFeatureValues.ExpressionMatrix")
                .withData(new UObject(data)))));
    }

    @Test
    public void testUploader2() throws Exception {
        File inputDir = new File("test/data/upload2");
        File exprTempFile = new File(workDir, "expression_output2.json");
        ExpressionUploader.main(new String[] { 
                "--input_directory", inputDir.getAbsolutePath(), 
                "--working_directory", exprTempFile.getParentFile().getAbsolutePath(), 
                "--output_file_name", exprTempFile.getName(),
                "--format_type", "Simple",
                "--data_type", "log-ratio2",
                "--data_scale", "2.0"});
        ExpressionMatrix matrix = UObject.getMapper().readValue(exprTempFile, ExpressionMatrix.class);
        Assert.assertNotNull(matrix.getData());
        Assert.assertEquals(11, matrix.getData().getColIds().size());
        Assert.assertEquals(2680, matrix.getData().getRowIds().size());
        Assert.assertEquals("log-ratio2", matrix.getType());
        Assert.assertEquals("2.0", matrix.getScale());
    }
    
    @Test
    public void testUploaderOthers() throws Exception {
        Exception error = null;
        for (int i = 3; i <= 6; i++) {
            File inputDir = new File("test/data/upload" + i);
            File inputFile = ExpressionUploader.findTabFile(inputDir);
            try {
                ExpressionMatrix matrix = ExpressionUploader.parse(null, inputFile, "Simple", 
                        null, false, null, null, null);
                //System.out.println("Parsing expression matrix in " + inputFile + ":");
                //System.out.println(matrix);
                Assert.assertNotNull(matrix.getData().getValues());
            } catch (Exception ex) {
                System.err.println("Error parsing expression matrix in " + inputFile + ":");
                ex.printStackTrace();
                if (error == null)
                    error = ex;
            }
        }
        if (error != null)
            throw error;
    }    
    
    @SuppressWarnings("unchecked")
    @Test
    public void testMappingToAliases() throws Exception {
        String expressionObjName = "Escherichia_coli_str_K-12_substr_MG1655.expression";
        String genomeObjName = "Escherichia_coli_str_K-12_substr_MG1655.genome";
        File inputDir = new File("test/data/upload6");
        File inputFile = new File(inputDir, "E_coli_v4_Build_6_subdata.tsv");
        WorkspaceClient wscl = getWsClient();
        Map<String, Object> genomeData = UObject.getMapper().readValue(new GZIPInputStream(
                new FileInputStream(new File(inputDir, "kb_g.1870.genome.json.gz"))), Map.class);
        wscl.saveObjects(new SaveObjectsParams().withWorkspace(testWsName).withObjects(Arrays.asList(
                new ObjectSaveData().withName(genomeObjName).withType("KBaseGenomes.Genome")
                .withData(new UObject(genomeData)))));
        ExpressionMatrix data = ExpressionUploader.parse(testWsName, inputFile, "Simple", 
                genomeObjName, true, "Unknown", "1.0", token);
        wscl.saveObjects(new SaveObjectsParams().withWorkspace(testWsName).withObjects(Arrays.asList(
                new ObjectSaveData().withName(expressionObjName)
                .withType("KBaseFeatureValues.ExpressionMatrix").withData(new UObject(data)))));
    }    

    private static WorkspaceClient getWsClient() throws Exception {
        WorkspaceClient wscl = new WorkspaceClient(new URL(wsUrl), token);
        wscl.setAuthAllowedForHttp(true);
        return wscl;
    }

    private static void writeFileLines(List<String> lines, File targetFile) throws IOException {
        PrintWriter pw = new PrintWriter(targetFile);
        for (String l : lines)
            pw.println(l);
        pw.close();
    }

    private static File prepareWorkDir(String testName) throws IOException {
        File tempDir = new File("work").getCanonicalFile();
        if (!tempDir.exists())
            tempDir.mkdirs();
        for (File dir : tempDir.listFiles()) {
            if (dir.isDirectory() && dir.getName().startsWith("test_" + testName + "_"))
                try {
                    deleteRecursively(dir);
                } catch (Exception e) {
                    System.out.println("Can not delete directory [" + dir.getName() + "]: " + e.getMessage());
                }
        }
        File workDir = new File(tempDir, "test_" + testName + "_" + System.currentTimeMillis());
        if (!workDir.exists())
            workDir.mkdir();
        return workDir;
    }
    
    private static void deleteRecursively(File fileOrDir) {
        if (fileOrDir.isDirectory() && !Files.isSymbolicLink(fileOrDir.toPath()))
            for (File f : fileOrDir.listFiles()) 
                deleteRecursively(f);
        fileOrDir.delete();
    }

}
