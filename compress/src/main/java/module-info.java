/**
 * Provides abstractions and utilities for HTTP content compression.
 * <p>
 * The {@code amayaframework.compress} module defines:
 * <ul>
 *   <li>{@link io.github.amayaframework.compress.CompressEncoder} —
 *       an interface for stream encoders (gzip, deflate, brotli, identity).</li>
 *   <li>{@link io.github.amayaframework.compress.CompressManager} —
 *       a registry for managing available encoders.</li>
 *   <li>{@link io.github.amayaframework.compress.EncodingHeaderParser} —
 *       for parsing {@code Accept-Encoding} headers with q-values.</li>
 *   <li>{@link io.github.amayaframework.compress.CompressNegotiator} —
 *       for negotiating the best encoder based on client preferences.</li>
 *   <li>{@link io.github.amayaframework.compress.CompressNegotiatorBuilder} —
 *       a fluent builder for creating negotiators with default encoders and priorities.</li>
 * </ul>
 * <p>
 * Depends on {@code amayaframework.tokenize} for header tokenization.
 */
module amayaframework.compress {
    // Imports
    requires amayaframework.tokenize;
    // Exports
    exports io.github.amayaframework.compress;
}
