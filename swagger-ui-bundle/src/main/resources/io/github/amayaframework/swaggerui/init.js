window.onload = function () {
    window.ui = SwaggerUIBundle({
        // url: "https://petstore.swagger.io/v2/swagger.json",
        urls: [
            {
                "url": "https://petstore.swagger.io/v2/swagger.json",
                "name": "Petstore"
            }
        ],
        dom_id: '#swagger-ui',
        deepLinking: true,
        presets: [
            SwaggerUIBundle.presets.apis,
            SwaggerUIStandalonePreset
        ],
        plugins: [
            SwaggerUIBundle.plugins.DownloadUrl
        ],
        layout: "StandaloneLayout"
    });
};
