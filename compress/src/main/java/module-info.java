/**
 * TODO
 */
module amayaframework.compress {
    // Imports
    // Tokenizer
    requires amayaframework.tokenize;
    // Optional brotli dependency
    requires static com.aayushatharva.brotli4j;
    // Exports
    exports io.github.amayaframework.compress;
}
