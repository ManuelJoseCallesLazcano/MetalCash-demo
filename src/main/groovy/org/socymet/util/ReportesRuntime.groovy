package org.socymet.util

import groovy.util.logging.Slf4j
import org.springframework.core.io.Resource
import org.springframework.core.io.support.PathMatchingResourcePatternResolver

/**
 * Resuelve rutas de reportes Jasper a un directorio en disco. La PRIMERA vez extrae
 * `classpath:/reports/**` (los .jasper/.jrxml/subreportes/images empaquetados desde
 * src/main/resources/reports/) a una carpeta temporal, y devuelve rutas absolutas hacia ella.
 *
 * Reemplaza el frágil `servletContext.getRealPath("/reports…")`, que en un JAR ejecutable
 * devuelve null (no hay webapp explotado). Así la ÚNICA fuente de reportes es
 * src/main/resources/reports/ y no se necesita la copia en src/main/webapp/reports/.
 *
 * Uso (drop-in): reemplazar `servletContext.getRealPath("/reports/images/")` por
 * `ReportesRuntime.realPath("/reports/images/")` — misma semántica y mismo argumento.
 */
@Slf4j
class ReportesRuntime {

    private static volatile String BASE

    /** Directorio base (extraído) con el contenido de resources/reports. */
    static String base() {
        if (BASE) return BASE
        synchronized (ReportesRuntime) {
            if (BASE) return BASE
            File dir = new File(System.getProperty('java.io.tmpdir'), 'smart-reports')
            dir.mkdirs()
            int n = 0
            try {
                def resolver = new PathMatchingResourcePatternResolver(ReportesRuntime.class.classLoader)
                resolver.getResources('classpath*:/reports/**').each { Resource r ->
                    if (!r.readable) return
                    String url = r.URL.toString()
                    int idx = url.lastIndexOf('/reports/')
                    if (idx < 0) return
                    String rel = url.substring(idx + '/reports/'.length())
                    if (!rel || rel.endsWith('/')) return   // omitir marcadores de directorio
                    File dest = new File(dir, rel)
                    dest.parentFile.mkdirs()
                    r.inputStream.withCloseable { input -> dest.withOutputStream { out -> out << input } }
                    n++
                }
            } catch (e) {
                log.error("ReportesRuntime: error extrayendo classpath:/reports a ${dir}", e)
            }
            log.info("ReportesRuntime: ${n} archivo(s) de reportes extraídos a ${dir}")
            BASE = dir.absolutePath
            return BASE
        }
    }

    /**
     * Equivalente a `servletContext.getRealPath(webPath)` para rutas bajo `/reports`.
     * Ej.: realPath("/reports") → &lt;temp&gt;/smart-reports ; realPath("/reports/images/") → …/images/
     */
    static String realPath(String webPath) {
        if (webPath == null) return base()
        String rel = webPath.startsWith('/reports') ? webPath.substring('/reports'.length()) : webPath
        base() + rel
    }
}
