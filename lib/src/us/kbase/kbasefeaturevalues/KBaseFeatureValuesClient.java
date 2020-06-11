package us.kbase.kbasefeaturevalues;

import com.fasterxml.jackson.core.type.TypeReference;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import us.kbase.auth.AuthToken;
import us.kbase.common.service.JsonClientCaller;
import us.kbase.common.service.JsonClientException;
import us.kbase.common.service.RpcContext;
import us.kbase.common.service.UnauthorizedException;

/**
 * <p>Original spec-file module name: KBaseFeatureValues</p>
 * <pre>
 * The KBaseFeatureValues set of data types and service provides a mechanism for
 * representing numeric values associated with genome features and conditions, together
 * with some basic operations on this data.  Essentially, the data is stored as a simple
 * 2D matrix of floating point numbers.  Currently, this is exposed as support for
 * expression data and single gene knockout fitness data.  (Fitness data being growth
 * rate relative to WT growth with the specified single gene knockout in a specified
 * condition).
 * The operations supported on this data is simple clustering of genes and clustering 
 * related tools.
 * </pre>
 */
public class KBaseFeatureValuesClient {
    private JsonClientCaller caller;
    private String serviceVersion = null;


    /** Constructs a client with a custom URL and no user credentials.
     * @param url the URL of the service.
     */
    public KBaseFeatureValuesClient(URL url) {
        caller = new JsonClientCaller(url);
    }
    /** Constructs a client with a custom URL.
     * @param url the URL of the service.
     * @param token the user's authorization token.
     * @throws UnauthorizedException if the token is not valid.
     * @throws IOException if an IOException occurs when checking the token's
     * validity.
     */
    public KBaseFeatureValuesClient(URL url, AuthToken token) throws UnauthorizedException, IOException {
        caller = new JsonClientCaller(url, token);
    }

    /** Constructs a client with a custom URL.
     * @param url the URL of the service.
     * @param user the user name.
     * @param password the password for the user name.
     * @throws UnauthorizedException if the credentials are not valid.
     * @throws IOException if an IOException occurs when checking the user's
     * credentials.
     */
    public KBaseFeatureValuesClient(URL url, String user, String password) throws UnauthorizedException, IOException {
        caller = new JsonClientCaller(url, user, password);
    }

    /** Constructs a client with a custom URL
     * and a custom authorization service URL.
     * @param url the URL of the service.
     * @param user the user name.
     * @param password the password for the user name.
     * @param auth the URL of the authorization server.
     * @throws UnauthorizedException if the credentials are not valid.
     * @throws IOException if an IOException occurs when checking the user's
     * credentials.
     */
    public KBaseFeatureValuesClient(URL url, String user, String password, URL auth) throws UnauthorizedException, IOException {
        caller = new JsonClientCaller(url, user, password, auth);
    }

    /** Get the token this client uses to communicate with the server.
     * @return the authorization token.
     */
    public AuthToken getToken() {
        return caller.getToken();
    }

    /** Get the URL of the service with which this client communicates.
     * @return the service URL.
     */
    public URL getURL() {
        return caller.getURL();
    }

    /** Set the timeout between establishing a connection to a server and
     * receiving a response. A value of zero or null implies no timeout.
     * @param milliseconds the milliseconds to wait before timing out when
     * attempting to read from a server.
     */
    public void setConnectionReadTimeOut(Integer milliseconds) {
        this.caller.setConnectionReadTimeOut(milliseconds);
    }

    /** Check if this client allows insecure http (vs https) connections.
     * @return true if insecure connections are allowed.
     */
    public boolean isInsecureHttpConnectionAllowed() {
        return caller.isInsecureHttpConnectionAllowed();
    }

    /** Deprecated. Use isInsecureHttpConnectionAllowed().
     * @deprecated
     */
    public boolean isAuthAllowedForHttp() {
        return caller.isAuthAllowedForHttp();
    }

    /** Set whether insecure http (vs https) connections should be allowed by
     * this client.
     * @param allowed true to allow insecure connections. Default false
     */
    public void setIsInsecureHttpConnectionAllowed(boolean allowed) {
        caller.setInsecureHttpConnectionAllowed(allowed);
    }

    /** Deprecated. Use setIsInsecureHttpConnectionAllowed().
     * @deprecated
     */
    public void setAuthAllowedForHttp(boolean isAuthAllowedForHttp) {
        caller.setAuthAllowedForHttp(isAuthAllowedForHttp);
    }

    /** Set whether all SSL certificates, including self-signed certificates,
     * should be trusted.
     * @param trustAll true to trust all certificates. Default false.
     */
    public void setAllSSLCertificatesTrusted(final boolean trustAll) {
        caller.setAllSSLCertificatesTrusted(trustAll);
    }
    
    /** Check if this client trusts all SSL certificates, including
     * self-signed certificates.
     * @return true if all certificates are trusted.
     */
    public boolean isAllSSLCertificatesTrusted() {
        return caller.isAllSSLCertificatesTrusted();
    }
    /** Sets streaming mode on. In this case, the data will be streamed to
     * the server in chunks as it is read from disk rather than buffered in
     * memory. Many servers are not compatible with this feature.
     * @param streamRequest true to set streaming mode on, false otherwise.
     */
    public void setStreamingModeOn(boolean streamRequest) {
        caller.setStreamingModeOn(streamRequest);
    }

    /** Returns true if streaming mode is on.
     * @return true if streaming mode is on.
     */
    public boolean isStreamingModeOn() {
        return caller.isStreamingModeOn();
    }

    public void _setFileForNextRpcResponse(File f) {
        caller.setFileForNextRpcResponse(f);
    }

    public String getServiceVersion() {
        return this.serviceVersion;
    }

    public void setServiceVersion(String newValue) {
        this.serviceVersion = newValue;
    }

    /**
     * <p>Original spec-file function name: estimate_k</p>
     * <pre>
     * Used as an analysis step before generating clusters using K-means clustering, this method
     * provides an estimate of K by [...]
     * </pre>
     * @param   params   instance of type {@link us.kbase.kbasefeaturevalues.EstimateKParams EstimateKParams}
     * @return   parameter "output" of type {@link us.kbase.kbasefeaturevalues.EstimateKResult EstimateKResult}
     * @throws IOException if an IO exception occurs
     * @throws JsonClientException if a JSON RPC exception occurs
     */
    public EstimateKResult estimateK(EstimateKParams params, RpcContext... jsonRpcContext) throws IOException, JsonClientException {
        List<Object> args = new ArrayList<Object>();
        args.add(params);
        TypeReference<List<EstimateKResult>> retType = new TypeReference<List<EstimateKResult>>() {};
        List<EstimateKResult> res = caller.jsonrpcCall("KBaseFeatureValues.estimate_k", args, retType, true, true, jsonRpcContext, this.serviceVersion);
        return res.get(0);
    }

    /**
     * <p>Original spec-file function name: estimate_k_new</p>
     * <pre>
     * Used as an analysis step before generating clusters using K-means clustering, this method
     * provides an estimate of K by [...]
     * </pre>
     * @param   params   instance of type {@link us.kbase.kbasefeaturevalues.EstimateKParamsNew EstimateKParamsNew}
     * @return   parameter "output" of type {@link us.kbase.kbasefeaturevalues.EstimateKResult EstimateKResult}
     * @throws IOException if an IO exception occurs
     * @throws JsonClientException if a JSON RPC exception occurs
     */
    public EstimateKResult estimateKNew(EstimateKParamsNew params, RpcContext... jsonRpcContext) throws IOException, JsonClientException {
        List<Object> args = new ArrayList<Object>();
        args.add(params);
        TypeReference<List<EstimateKResult>> retType = new TypeReference<List<EstimateKResult>>() {};
        List<EstimateKResult> res = caller.jsonrpcCall("KBaseFeatureValues.estimate_k_new", args, retType, true, true, jsonRpcContext, this.serviceVersion);
        return res.get(0);
    }

    /**
     * <p>Original spec-file function name: cluster_k_means</p>
     * <pre>
     * Clusters features by K-means clustering.
     * </pre>
     * @param   params   instance of type {@link us.kbase.kbasefeaturevalues.ClusterKMeansParams ClusterKMeansParams}
     * @return   parameter "workspace_ref" of String
     * @throws IOException if an IO exception occurs
     * @throws JsonClientException if a JSON RPC exception occurs
     */
    public String clusterKMeans(ClusterKMeansParams params, RpcContext... jsonRpcContext) throws IOException, JsonClientException {
        List<Object> args = new ArrayList<Object>();
        args.add(params);
        TypeReference<List<String>> retType = new TypeReference<List<String>>() {};
        List<String> res = caller.jsonrpcCall("KBaseFeatureValues.cluster_k_means", args, retType, true, true, jsonRpcContext, this.serviceVersion);
        return res.get(0);
    }

    /**
     * <p>Original spec-file function name: cluster_hierarchical</p>
     * <pre>
     * Clusters features by hierarchical clustering.
     * </pre>
     * @param   params   instance of type {@link us.kbase.kbasefeaturevalues.ClusterHierarchicalParams ClusterHierarchicalParams}
     * @return   parameter "workspace_ref" of String
     * @throws IOException if an IO exception occurs
     * @throws JsonClientException if a JSON RPC exception occurs
     */
    public String clusterHierarchical(ClusterHierarchicalParams params, RpcContext... jsonRpcContext) throws IOException, JsonClientException {
        List<Object> args = new ArrayList<Object>();
        args.add(params);
        TypeReference<List<String>> retType = new TypeReference<List<String>>() {};
        List<String> res = caller.jsonrpcCall("KBaseFeatureValues.cluster_hierarchical", args, retType, true, true, jsonRpcContext, this.serviceVersion);
        return res.get(0);
    }

    /**
     * <p>Original spec-file function name: clusters_from_dendrogram</p>
     * <pre>
     * Given a FeatureClusters with a dendogram built from a hierarchical clustering
     * method, this function creates new clusters by cutting the dendogram at
     * a specific hieght or by some other approach.
     * </pre>
     * @param   params   instance of type {@link us.kbase.kbasefeaturevalues.ClustersFromDendrogramParams ClustersFromDendrogramParams}
     * @return   parameter "workspace_ref" of String
     * @throws IOException if an IO exception occurs
     * @throws JsonClientException if a JSON RPC exception occurs
     */
    public String clustersFromDendrogram(ClustersFromDendrogramParams params, RpcContext... jsonRpcContext) throws IOException, JsonClientException {
        List<Object> args = new ArrayList<Object>();
        args.add(params);
        TypeReference<List<String>> retType = new TypeReference<List<String>>() {};
        List<String> res = caller.jsonrpcCall("KBaseFeatureValues.clusters_from_dendrogram", args, retType, true, true, jsonRpcContext, this.serviceVersion);
        return res.get(0);
    }

    /**
     * <p>Original spec-file function name: evaluate_clusterset_quality</p>
     * <pre>
     * Given a FeatureClusters with a dendogram built from a hierarchical clustering
     * method, this function creates new clusters by cutting the dendogram at
     * a specific hieght or by some other approach.
     * </pre>
     * @param   params   instance of type {@link us.kbase.kbasefeaturevalues.EvaluateClustersetQualityParams EvaluateClustersetQualityParams}
     * @throws IOException if an IO exception occurs
     * @throws JsonClientException if a JSON RPC exception occurs
     */
    public void evaluateClustersetQuality(EvaluateClustersetQualityParams params, RpcContext... jsonRpcContext) throws IOException, JsonClientException {
        List<Object> args = new ArrayList<Object>();
        args.add(params);
        TypeReference<Object> retType = new TypeReference<Object>() {};
        caller.jsonrpcCall("KBaseFeatureValues.evaluate_clusterset_quality", args, retType, false, true, jsonRpcContext, this.serviceVersion);
    }

    /**
     * <p>Original spec-file function name: validate_matrix</p>
     * <pre>
     * </pre>
     * @param   params   instance of type {@link us.kbase.kbasefeaturevalues.ValidateMatrixParams ValidateMatrixParams}
     * @throws IOException if an IO exception occurs
     * @throws JsonClientException if a JSON RPC exception occurs
     */
    public void validateMatrix(ValidateMatrixParams params, RpcContext... jsonRpcContext) throws IOException, JsonClientException {
        List<Object> args = new ArrayList<Object>();
        args.add(params);
        TypeReference<Object> retType = new TypeReference<Object>() {};
        caller.jsonrpcCall("KBaseFeatureValues.validate_matrix", args, retType, false, false, jsonRpcContext, this.serviceVersion);
    }

    /**
     * <p>Original spec-file function name: correct_matrix</p>
     * <pre>
     * </pre>
     * @param   params   instance of type {@link us.kbase.kbasefeaturevalues.CorrectMatrixParams CorrectMatrixParams}
     * @return   parameter "workspace_ref" of String
     * @throws IOException if an IO exception occurs
     * @throws JsonClientException if a JSON RPC exception occurs
     */
    public String correctMatrix(CorrectMatrixParams params, RpcContext... jsonRpcContext) throws IOException, JsonClientException {
        List<Object> args = new ArrayList<Object>();
        args.add(params);
        TypeReference<List<String>> retType = new TypeReference<List<String>>() {};
        List<String> res = caller.jsonrpcCall("KBaseFeatureValues.correct_matrix", args, retType, true, true, jsonRpcContext, this.serviceVersion);
        return res.get(0);
    }

    /**
     * <p>Original spec-file function name: reconnect_matrix_to_genome</p>
     * <pre>
     * </pre>
     * @param   params   instance of type {@link us.kbase.kbasefeaturevalues.ReconnectMatrixToGenomeParams ReconnectMatrixToGenomeParams}
     * @return   parameter "workspace_ref" of String
     * @throws IOException if an IO exception occurs
     * @throws JsonClientException if a JSON RPC exception occurs
     */
    public String reconnectMatrixToGenome(ReconnectMatrixToGenomeParams params, RpcContext... jsonRpcContext) throws IOException, JsonClientException {
        List<Object> args = new ArrayList<Object>();
        args.add(params);
        TypeReference<List<String>> retType = new TypeReference<List<String>>() {};
        List<String> res = caller.jsonrpcCall("KBaseFeatureValues.reconnect_matrix_to_genome", args, retType, true, true, jsonRpcContext, this.serviceVersion);
        return res.get(0);
    }

    /**
     * <p>Original spec-file function name: build_feature_set</p>
     * <pre>
     * </pre>
     * @param   params   instance of type {@link us.kbase.kbasefeaturevalues.BuildFeatureSetParams BuildFeatureSetParams}
     * @return   parameter "workspace_ref" of String
     * @throws IOException if an IO exception occurs
     * @throws JsonClientException if a JSON RPC exception occurs
     */
    public String buildFeatureSet(BuildFeatureSetParams params, RpcContext... jsonRpcContext) throws IOException, JsonClientException {
        List<Object> args = new ArrayList<Object>();
        args.add(params);
        TypeReference<List<String>> retType = new TypeReference<List<String>>() {};
        List<String> res = caller.jsonrpcCall("KBaseFeatureValues.build_feature_set", args, retType, true, true, jsonRpcContext, this.serviceVersion);
        return res.get(0);
    }

    /**
     * <p>Original spec-file function name: get_matrix_descriptor</p>
     * <pre>
     * </pre>
     * @param   arg1   instance of type {@link us.kbase.kbasefeaturevalues.GetMatrixDescriptorParams GetMatrixDescriptorParams}
     * @return   instance of type {@link us.kbase.kbasefeaturevalues.MatrixDescriptor MatrixDescriptor}
     * @throws IOException if an IO exception occurs
     * @throws JsonClientException if a JSON RPC exception occurs
     */
    public MatrixDescriptor getMatrixDescriptor(GetMatrixDescriptorParams arg1, RpcContext... jsonRpcContext) throws IOException, JsonClientException {
        List<Object> args = new ArrayList<Object>();
        args.add(arg1);
        TypeReference<List<MatrixDescriptor>> retType = new TypeReference<List<MatrixDescriptor>>() {};
        List<MatrixDescriptor> res = caller.jsonrpcCall("KBaseFeatureValues.get_matrix_descriptor", args, retType, true, true, jsonRpcContext, this.serviceVersion);
        return res.get(0);
    }

    /**
     * <p>Original spec-file function name: get_matrix_row_descriptors</p>
     * <pre>
     * </pre>
     * @param   arg1   instance of type {@link us.kbase.kbasefeaturevalues.GetMatrixItemDescriptorsParams GetMatrixItemDescriptorsParams}
     * @return   instance of list of type {@link us.kbase.kbasefeaturevalues.ItemDescriptor ItemDescriptor}
     * @throws IOException if an IO exception occurs
     * @throws JsonClientException if a JSON RPC exception occurs
     */
    public List<ItemDescriptor> getMatrixRowDescriptors(GetMatrixItemDescriptorsParams arg1, RpcContext... jsonRpcContext) throws IOException, JsonClientException {
        List<Object> args = new ArrayList<Object>();
        args.add(arg1);
        TypeReference<List<List<ItemDescriptor>>> retType = new TypeReference<List<List<ItemDescriptor>>>() {};
        List<List<ItemDescriptor>> res = caller.jsonrpcCall("KBaseFeatureValues.get_matrix_row_descriptors", args, retType, true, true, jsonRpcContext, this.serviceVersion);
        return res.get(0);
    }

    /**
     * <p>Original spec-file function name: get_matrix_column_descriptors</p>
     * <pre>
     * </pre>
     * @param   arg1   instance of type {@link us.kbase.kbasefeaturevalues.GetMatrixItemDescriptorsParams GetMatrixItemDescriptorsParams}
     * @return   instance of list of type {@link us.kbase.kbasefeaturevalues.ItemDescriptor ItemDescriptor}
     * @throws IOException if an IO exception occurs
     * @throws JsonClientException if a JSON RPC exception occurs
     */
    public List<ItemDescriptor> getMatrixColumnDescriptors(GetMatrixItemDescriptorsParams arg1, RpcContext... jsonRpcContext) throws IOException, JsonClientException {
        List<Object> args = new ArrayList<Object>();
        args.add(arg1);
        TypeReference<List<List<ItemDescriptor>>> retType = new TypeReference<List<List<ItemDescriptor>>>() {};
        List<List<ItemDescriptor>> res = caller.jsonrpcCall("KBaseFeatureValues.get_matrix_column_descriptors", args, retType, true, true, jsonRpcContext, this.serviceVersion);
        return res.get(0);
    }

    /**
     * <p>Original spec-file function name: get_matrix_rows_stat</p>
     * <pre>
     * </pre>
     * @param   arg1   instance of type {@link us.kbase.kbasefeaturevalues.GetMatrixItemsStatParams GetMatrixItemsStatParams}
     * @return   instance of list of type {@link us.kbase.kbasefeaturevalues.ItemStat ItemStat}
     * @throws IOException if an IO exception occurs
     * @throws JsonClientException if a JSON RPC exception occurs
     */
    public List<ItemStat> getMatrixRowsStat(GetMatrixItemsStatParams arg1, RpcContext... jsonRpcContext) throws IOException, JsonClientException {
        List<Object> args = new ArrayList<Object>();
        args.add(arg1);
        TypeReference<List<List<ItemStat>>> retType = new TypeReference<List<List<ItemStat>>>() {};
        List<List<ItemStat>> res = caller.jsonrpcCall("KBaseFeatureValues.get_matrix_rows_stat", args, retType, true, true, jsonRpcContext, this.serviceVersion);
        return res.get(0);
    }

    /**
     * <p>Original spec-file function name: get_matrix_columns_stat</p>
     * <pre>
     * </pre>
     * @param   arg1   instance of type {@link us.kbase.kbasefeaturevalues.GetMatrixItemsStatParams GetMatrixItemsStatParams}
     * @return   instance of list of type {@link us.kbase.kbasefeaturevalues.ItemStat ItemStat}
     * @throws IOException if an IO exception occurs
     * @throws JsonClientException if a JSON RPC exception occurs
     */
    public List<ItemStat> getMatrixColumnsStat(GetMatrixItemsStatParams arg1, RpcContext... jsonRpcContext) throws IOException, JsonClientException {
        List<Object> args = new ArrayList<Object>();
        args.add(arg1);
        TypeReference<List<List<ItemStat>>> retType = new TypeReference<List<List<ItemStat>>>() {};
        List<List<ItemStat>> res = caller.jsonrpcCall("KBaseFeatureValues.get_matrix_columns_stat", args, retType, true, true, jsonRpcContext, this.serviceVersion);
        return res.get(0);
    }

    /**
     * <p>Original spec-file function name: get_matrix_row_sets_stat</p>
     * <pre>
     * </pre>
     * @param   arg1   instance of type {@link us.kbase.kbasefeaturevalues.GetMatrixSetsStatParams GetMatrixSetsStatParams}
     * @return   instance of list of type {@link us.kbase.kbasefeaturevalues.ItemSetStat ItemSetStat}
     * @throws IOException if an IO exception occurs
     * @throws JsonClientException if a JSON RPC exception occurs
     */
    public List<ItemSetStat> getMatrixRowSetsStat(GetMatrixSetsStatParams arg1, RpcContext... jsonRpcContext) throws IOException, JsonClientException {
        List<Object> args = new ArrayList<Object>();
        args.add(arg1);
        TypeReference<List<List<ItemSetStat>>> retType = new TypeReference<List<List<ItemSetStat>>>() {};
        List<List<ItemSetStat>> res = caller.jsonrpcCall("KBaseFeatureValues.get_matrix_row_sets_stat", args, retType, true, true, jsonRpcContext, this.serviceVersion);
        return res.get(0);
    }

    /**
     * <p>Original spec-file function name: get_matrix_column_sets_stat</p>
     * <pre>
     * </pre>
     * @param   arg1   instance of type {@link us.kbase.kbasefeaturevalues.GetMatrixSetsStatParams GetMatrixSetsStatParams}
     * @return   instance of list of type {@link us.kbase.kbasefeaturevalues.ItemSetStat ItemSetStat}
     * @throws IOException if an IO exception occurs
     * @throws JsonClientException if a JSON RPC exception occurs
     */
    public List<ItemSetStat> getMatrixColumnSetsStat(GetMatrixSetsStatParams arg1, RpcContext... jsonRpcContext) throws IOException, JsonClientException {
        List<Object> args = new ArrayList<Object>();
        args.add(arg1);
        TypeReference<List<List<ItemSetStat>>> retType = new TypeReference<List<List<ItemSetStat>>>() {};
        List<List<ItemSetStat>> res = caller.jsonrpcCall("KBaseFeatureValues.get_matrix_column_sets_stat", args, retType, true, true, jsonRpcContext, this.serviceVersion);
        return res.get(0);
    }

    /**
     * <p>Original spec-file function name: get_matrix_stat</p>
     * <pre>
     * </pre>
     * @param   arg1   instance of type {@link us.kbase.kbasefeaturevalues.GetMatrixStatParams GetMatrixStatParams}
     * @return   instance of type {@link us.kbase.kbasefeaturevalues.MatrixStat MatrixStat}
     * @throws IOException if an IO exception occurs
     * @throws JsonClientException if a JSON RPC exception occurs
     */
    public MatrixStat getMatrixStat(GetMatrixStatParams arg1, RpcContext... jsonRpcContext) throws IOException, JsonClientException {
        List<Object> args = new ArrayList<Object>();
        args.add(arg1);
        TypeReference<List<MatrixStat>> retType = new TypeReference<List<MatrixStat>>() {};
        List<MatrixStat> res = caller.jsonrpcCall("KBaseFeatureValues.get_matrix_stat", args, retType, true, true, jsonRpcContext, this.serviceVersion);
        return res.get(0);
    }

    /**
     * <p>Original spec-file function name: get_submatrix_stat</p>
     * <pre>
     * </pre>
     * @param   arg1   instance of type {@link us.kbase.kbasefeaturevalues.GetSubmatrixStatParams GetSubmatrixStatParams}
     * @return   instance of type {@link us.kbase.kbasefeaturevalues.SubmatrixStat SubmatrixStat}
     * @throws IOException if an IO exception occurs
     * @throws JsonClientException if a JSON RPC exception occurs
     */
    public SubmatrixStat getSubmatrixStat(GetSubmatrixStatParams arg1, RpcContext... jsonRpcContext) throws IOException, JsonClientException {
        List<Object> args = new ArrayList<Object>();
        args.add(arg1);
        TypeReference<List<SubmatrixStat>> retType = new TypeReference<List<SubmatrixStat>>() {};
        List<SubmatrixStat> res = caller.jsonrpcCall("KBaseFeatureValues.get_submatrix_stat", args, retType, true, true, jsonRpcContext, this.serviceVersion);
        return res.get(0);
    }

    /**
     * <p>Original spec-file function name: tsv_file_to_matrix</p>
     * <pre>
     * </pre>
     * @param   params   instance of type {@link us.kbase.kbasefeaturevalues.TsvFileToMatrixParams TsvFileToMatrixParams}
     * @return   instance of type {@link us.kbase.kbasefeaturevalues.TsvFileToMatrixOutput TsvFileToMatrixOutput}
     * @throws IOException if an IO exception occurs
     * @throws JsonClientException if a JSON RPC exception occurs
     */
    public TsvFileToMatrixOutput tsvFileToMatrix(TsvFileToMatrixParams params, RpcContext... jsonRpcContext) throws IOException, JsonClientException {
        List<Object> args = new ArrayList<Object>();
        args.add(params);
        TypeReference<List<TsvFileToMatrixOutput>> retType = new TypeReference<List<TsvFileToMatrixOutput>>() {};
        List<TsvFileToMatrixOutput> res = caller.jsonrpcCall("KBaseFeatureValues.tsv_file_to_matrix", args, retType, true, true, jsonRpcContext, this.serviceVersion);
        return res.get(0);
    }

    /**
     * <p>Original spec-file function name: matrix_to_tsv_file</p>
     * <pre>
     * </pre>
     * @param   params   instance of type {@link us.kbase.kbasefeaturevalues.MatrixToTsvFileParams MatrixToTsvFileParams}
     * @return   instance of type {@link us.kbase.kbasefeaturevalues.MatrixToTsvFileOutput MatrixToTsvFileOutput}
     * @throws IOException if an IO exception occurs
     * @throws JsonClientException if a JSON RPC exception occurs
     */
    public MatrixToTsvFileOutput matrixToTsvFile(MatrixToTsvFileParams params, RpcContext... jsonRpcContext) throws IOException, JsonClientException {
        List<Object> args = new ArrayList<Object>();
        args.add(params);
        TypeReference<List<MatrixToTsvFileOutput>> retType = new TypeReference<List<MatrixToTsvFileOutput>>() {};
        List<MatrixToTsvFileOutput> res = caller.jsonrpcCall("KBaseFeatureValues.matrix_to_tsv_file", args, retType, true, true, jsonRpcContext, this.serviceVersion);
        return res.get(0);
    }

    /**
     * <p>Original spec-file function name: export_matrix</p>
     * <pre>
     * </pre>
     * @param   params   instance of type {@link us.kbase.kbasefeaturevalues.ExportMatrixParams ExportMatrixParams}
     * @return   instance of type {@link us.kbase.kbasefeaturevalues.ExportMatrixOutput ExportMatrixOutput}
     * @throws IOException if an IO exception occurs
     * @throws JsonClientException if a JSON RPC exception occurs
     */
    public ExportMatrixOutput exportMatrix(ExportMatrixParams params, RpcContext... jsonRpcContext) throws IOException, JsonClientException {
        List<Object> args = new ArrayList<Object>();
        args.add(params);
        TypeReference<List<ExportMatrixOutput>> retType = new TypeReference<List<ExportMatrixOutput>>() {};
        List<ExportMatrixOutput> res = caller.jsonrpcCall("KBaseFeatureValues.export_matrix", args, retType, true, true, jsonRpcContext, this.serviceVersion);
        return res.get(0);
    }

    /**
     * <p>Original spec-file function name: clusters_to_file</p>
     * <pre>
     * </pre>
     * @param   params   instance of type {@link us.kbase.kbasefeaturevalues.ClustersToFileParams ClustersToFileParams}
     * @return   instance of type {@link us.kbase.kbasefeaturevalues.ClustersToFileOutput ClustersToFileOutput}
     * @throws IOException if an IO exception occurs
     * @throws JsonClientException if a JSON RPC exception occurs
     */
    public ClustersToFileOutput clustersToFile(ClustersToFileParams params, RpcContext... jsonRpcContext) throws IOException, JsonClientException {
        List<Object> args = new ArrayList<Object>();
        args.add(params);
        TypeReference<List<ClustersToFileOutput>> retType = new TypeReference<List<ClustersToFileOutput>>() {};
        List<ClustersToFileOutput> res = caller.jsonrpcCall("KBaseFeatureValues.clusters_to_file", args, retType, true, true, jsonRpcContext, this.serviceVersion);
        return res.get(0);
    }

    /**
     * <p>Original spec-file function name: export_clusters_tsv</p>
     * <pre>
     * </pre>
     * @param   params   instance of type {@link us.kbase.kbasefeaturevalues.ExportClustersTsvParams ExportClustersTsvParams}
     * @return   instance of type {@link us.kbase.kbasefeaturevalues.ExportClustersTsvOutput ExportClustersTsvOutput}
     * @throws IOException if an IO exception occurs
     * @throws JsonClientException if a JSON RPC exception occurs
     */
    public ExportClustersTsvOutput exportClustersTsv(ExportClustersTsvParams params, RpcContext... jsonRpcContext) throws IOException, JsonClientException {
        List<Object> args = new ArrayList<Object>();
        args.add(params);
        TypeReference<List<ExportClustersTsvOutput>> retType = new TypeReference<List<ExportClustersTsvOutput>>() {};
        List<ExportClustersTsvOutput> res = caller.jsonrpcCall("KBaseFeatureValues.export_clusters_tsv", args, retType, true, true, jsonRpcContext, this.serviceVersion);
        return res.get(0);
    }

    /**
     * <p>Original spec-file function name: export_clusters_sif</p>
     * <pre>
     * </pre>
     * @param   params   instance of type {@link us.kbase.kbasefeaturevalues.ExportClustersSifParams ExportClustersSifParams}
     * @return   instance of type {@link us.kbase.kbasefeaturevalues.ExportClustersSifOutput ExportClustersSifOutput}
     * @throws IOException if an IO exception occurs
     * @throws JsonClientException if a JSON RPC exception occurs
     */
    public ExportClustersSifOutput exportClustersSif(ExportClustersSifParams params, RpcContext... jsonRpcContext) throws IOException, JsonClientException {
        List<Object> args = new ArrayList<Object>();
        args.add(params);
        TypeReference<List<ExportClustersSifOutput>> retType = new TypeReference<List<ExportClustersSifOutput>>() {};
        List<ExportClustersSifOutput> res = caller.jsonrpcCall("KBaseFeatureValues.export_clusters_sif", args, retType, true, true, jsonRpcContext, this.serviceVersion);
        return res.get(0);
    }

    public Map<String, Object> status(RpcContext... jsonRpcContext) throws IOException, JsonClientException {
        List<Object> args = new ArrayList<Object>();
        TypeReference<List<Map<String, Object>>> retType = new TypeReference<List<Map<String, Object>>>() {};
        List<Map<String, Object>> res = caller.jsonrpcCall("KBaseFeatureValues.status", args, retType, true, false, jsonRpcContext, this.serviceVersion);
        return res.get(0);
    }
}
