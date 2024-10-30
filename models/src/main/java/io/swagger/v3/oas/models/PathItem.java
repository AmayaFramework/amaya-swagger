package io.swagger.v3.oas.models;

import io.swagger.v3.oas.models.annotations.OpenAPI31;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.servers.Server;

import java.util.*;

/**
 * PathItem
 *
 * @see "https://github.com/OAI/OpenAPI-Specification/blob/3.0.1/versions/3.0.1.md#pathItemObject"
 * @see "https://github.com/OAI/OpenAPI-Specification/blob/3.1.0/versions/3.1.0.md#pathItemObject"
 */
public class PathItem {
    private String summary = null;
    private String description = null;
    private Operation get = null;
    private Operation put = null;
    private Operation post = null;
    private Operation delete = null;
    private Operation options = null;
    private Operation head = null;
    private Operation patch = null;
    private Operation trace = null;
    private List<Server> servers = null;
    private List<Parameter> parameters = null;
    private String $ref = null;
    private Map<String, Object> extensions = null;

    /**
     * returns the summary property from a PathItem instance.
     *
     * @return String summary
     **/
    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public PathItem summary(String summary) {
        this.summary = summary;
        return this;
    }

    /**
     * returns the description property from a PathItem instance.
     *
     * @return String description
     **/
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public PathItem description(String description) {
        this.description = description;
        return this;
    }

    /**
     * returns the get property from a PathItem instance.
     *
     * @return Operation get
     **/
    public Operation getGet() {
        return get;
    }

    public void setGet(Operation get) {
        this.get = get;
    }

    public PathItem get(Operation get) {
        this.get = get;
        return this;
    }

    /**
     * returns the put property from a PathItem instance.
     *
     * @return Operation put
     **/
    public Operation getPut() {
        return put;
    }

    public void setPut(Operation put) {
        this.put = put;
    }

    public PathItem put(Operation put) {
        this.put = put;
        return this;
    }

    /**
     * returns the post property from a PathItem instance.
     *
     * @return Operation post
     **/
    public Operation getPost() {
        return post;
    }

    public void setPost(Operation post) {
        this.post = post;
    }

    public PathItem post(Operation post) {
        this.post = post;
        return this;
    }

    /**
     * returns the delete property from a PathItem instance.
     *
     * @return Operation delete
     **/
    public Operation getDelete() {
        return delete;
    }

    public void setDelete(Operation delete) {
        this.delete = delete;
    }

    public PathItem delete(Operation delete) {
        this.delete = delete;
        return this;
    }

    /**
     * returns the options property from a PathItem instance.
     *
     * @return Operation options
     **/
    public Operation getOptions() {
        return options;
    }

    public void setOptions(Operation options) {
        this.options = options;
    }

    public PathItem options(Operation options) {
        this.options = options;
        return this;
    }

    /**
     * returns the head property from a PathItem instance.
     *
     * @return Operation head
     **/
    public Operation getHead() {
        return head;
    }

    public void setHead(Operation head) {
        this.head = head;
    }

    public PathItem head(Operation head) {
        this.head = head;
        return this;
    }

    /**
     * returns the patch property from a PathItem instance.
     *
     * @return Operation patch
     **/
    public Operation getPatch() {
        return patch;
    }

    public void setPatch(Operation patch) {
        this.patch = patch;
    }

    public PathItem patch(Operation patch) {
        this.patch = patch;
        return this;
    }

    /**
     * returns the trace property from a PathItem instance.
     *
     * @return Operation trace
     **/
    public Operation getTrace() {
        return trace;
    }

    public void setTrace(Operation trace) {
        this.trace = trace;
    }

    public PathItem trace(Operation trace) {
        this.trace = trace;
        return this;
    }

    public List<Operation> readOperations() {
        var allOperations = new ArrayList<Operation>();
        if (this.get != null) {
            allOperations.add(this.get);
        }
        if (this.put != null) {
            allOperations.add(this.put);
        }
        if (this.head != null) {
            allOperations.add(this.head);
        }
        if (this.post != null) {
            allOperations.add(this.post);
        }
        if (this.delete != null) {
            allOperations.add(this.delete);
        }
        if (this.patch != null) {
            allOperations.add(this.patch);
        }
        if (this.options != null) {
            allOperations.add(this.options);
        }
        if (this.trace != null) {
            allOperations.add(this.trace);
        }

        return allOperations;
    }

    public void operation(HttpMethod method, Operation operation) {
        switch (method) {
            case PATCH:
                this.patch = operation;
                break;
            case POST:
                this.post = operation;
                break;
            case PUT:
                this.put = operation;
                break;
            case GET:
                this.get = operation;
                break;
            case OPTIONS:
                this.options = operation;
                break;
            case TRACE:
                this.trace = operation;
                break;
            case HEAD:
                this.head = operation;
                break;
            case DELETE:
                this.delete = operation;
                break;
            default:
        }
    }

    public Map<HttpMethod, Operation> readOperationsMap() {
        var result = new LinkedHashMap<HttpMethod, Operation>();
        if (this.get != null) {
            result.put(HttpMethod.GET, this.get);
        }
        if (this.put != null) {
            result.put(HttpMethod.PUT, this.put);
        }
        if (this.post != null) {
            result.put(HttpMethod.POST, this.post);
        }
        if (this.delete != null) {
            result.put(HttpMethod.DELETE, this.delete);
        }
        if (this.patch != null) {
            result.put(HttpMethod.PATCH, this.patch);
        }
        if (this.head != null) {
            result.put(HttpMethod.HEAD, this.head);
        }
        if (this.options != null) {
            result.put(HttpMethod.OPTIONS, this.options);
        }
        if (this.trace != null) {
            result.put(HttpMethod.TRACE, this.trace);
        }
        return result;
    }

    /**
     * returns the servers property from a PathItem instance.
     *
     * @return List&lt;Server&gt; servers
     **/
    public List<Server> getServers() {
        return servers;
    }

    public void setServers(List<Server> servers) {
        this.servers = servers;
    }

    public PathItem servers(List<Server> servers) {
        this.servers = servers;
        return this;
    }

    public PathItem addServersItem(Server serversItem) {
        if (this.servers == null) {
            this.servers = new ArrayList<>();
        }
        this.servers.add(serversItem);
        return this;
    }

    /**
     * returns the parameters property from a PathItem instance.
     *
     * @return List&lt;Parameter&gt; parameters
     **/
    public List<Parameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<Parameter> parameters) {
        this.parameters = parameters;
    }

    public PathItem parameters(List<Parameter> parameters) {
        this.parameters = parameters;
        return this;
    }

    public PathItem addParametersItem(Parameter parametersItem) {
        if (this.parameters == null) {
            this.parameters = new ArrayList<>();
        }
        this.parameters.add(parametersItem);
        return this;
    }

    public Map<String, Object> getExtensions() {
        return extensions;
    }

    public void setExtensions(Map<String, Object> extensions) {
        this.extensions = extensions;
    }

    public void addExtension(String name, Object value) {
        if (name == null || !name.startsWith("x-")) {
            return;
        }
        if (this.extensions == null) {
            this.extensions = new java.util.LinkedHashMap<>();
        }
        this.extensions.put(name, value);
    }

    @OpenAPI31
    public void addExtension31(String name, Object value) {
        if (name != null && (name.startsWith("x-oas-") || name.startsWith("x-oai-"))) {
            return;
        }
        addExtension(name, value);
    }

    public PathItem extensions(Map<String, Object> extensions) {
        this.extensions = extensions;
        return this;
    }

    /**
     * returns the ref property from a PathItem instance.
     *
     * @return String ref
     **/
    public String get$ref() {
        return $ref;
    }

    public void set$ref(String $ref) {
        this.$ref = $ref;
    }

    public PathItem $ref(String $ref) {
        set$ref($ref);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PathItem)) {
            return false;
        }

        var pathItem = (PathItem) o;

        if (!Objects.equals(summary, pathItem.summary)) {
            return false;
        }
        if (!Objects.equals(description, pathItem.description)) {
            return false;
        }
        if (!Objects.equals(get, pathItem.get)) {
            return false;
        }
        if (!Objects.equals(put, pathItem.put)) {
            return false;
        }
        if (!Objects.equals(post, pathItem.post)) {
            return false;
        }
        if (!Objects.equals(delete, pathItem.delete)) {
            return false;
        }
        if (!Objects.equals(options, pathItem.options)) {
            return false;
        }
        if (!Objects.equals(head, pathItem.head)) {
            return false;
        }
        if (!Objects.equals(patch, pathItem.patch)) {
            return false;
        }
        if (!Objects.equals(trace, pathItem.trace)) {
            return false;
        }
        if (!Objects.equals(servers, pathItem.servers)) {
            return false;
        }
        if (!Objects.equals(parameters, pathItem.parameters)) {
            return false;
        }
        if (!Objects.equals($ref, pathItem.$ref)) {
            return false;
        }
        return Objects.equals(extensions, pathItem.extensions);
    }

    @Override
    public int hashCode() {
        var result = summary != null ? summary.hashCode() : 0;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (get != null ? get.hashCode() : 0);
        result = 31 * result + (put != null ? put.hashCode() : 0);
        result = 31 * result + (post != null ? post.hashCode() : 0);
        result = 31 * result + (delete != null ? delete.hashCode() : 0);
        result = 31 * result + (options != null ? options.hashCode() : 0);
        result = 31 * result + (head != null ? head.hashCode() : 0);
        result = 31 * result + (patch != null ? patch.hashCode() : 0);
        result = 31 * result + (trace != null ? trace.hashCode() : 0);
        result = 31 * result + (servers != null ? servers.hashCode() : 0);
        result = 31 * result + (parameters != null ? parameters.hashCode() : 0);
        result = 31 * result + ($ref != null ? $ref.hashCode() : 0);
        result = 31 * result + (extensions != null ? extensions.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "class PathItem {\n" +
                "    summary: " + toIndentedString(summary) + "\n" +
                "    description: " + toIndentedString(description) + "\n" +
                "    get: " + toIndentedString(get) + "\n" +
                "    put: " + toIndentedString(put) + "\n" +
                "    post: " + toIndentedString(post) + "\n" +
                "    delete: " + toIndentedString(delete) + "\n" +
                "    options: " + toIndentedString(options) + "\n" +
                "    head: " + toIndentedString(head) + "\n" +
                "    patch: " + toIndentedString(patch) + "\n" +
                "    trace: " + toIndentedString(trace) + "\n" +
                "    servers: " + toIndentedString(servers) + "\n" +
                "    parameters: " + toIndentedString(parameters) + "\n" +
                "    $ref: " + toIndentedString($ref) + "\n" +
                "}";
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }

    public enum HttpMethod {
        POST,
        GET,
        PUT,
        PATCH,
        DELETE,
        HEAD,
        OPTIONS,
        TRACE
    }
}
