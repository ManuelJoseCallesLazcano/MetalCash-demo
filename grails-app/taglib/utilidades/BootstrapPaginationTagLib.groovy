package utilidades

class BootstrapPaginationTagLib {

    static namespace = "g"

    def paginate = { attrs ->
        def total = attrs.int('total') ?: 0
        if (total == 0) return

        def max = params.int('max') ?: attrs.int('max') ?: 10
        def offset = params.int('offset') ?: 0
        def currentPage = (int) (offset / max) + 1
        def totalPages = (int) Math.ceil(total / max)

        if (totalPages <= 1) return

        def action = attrs.action ?: params.action ?: 'index'
        def ctrl = attrs.controller ?: controllerName

        // Preserve sort, order and any caller-supplied extra params
        def base = [max: max]
        if (params.sort)  base.sort  = params.sort
        if (params.order) base.order = params.order
        if (attrs.params) base += attrs.params

        def link = { int page ->
            createLink(controller: ctrl, action: action,
                       params: base + [offset: (page - 1) * max])
        }

        def w = out
        w << '<ul class="pagination pagination-sm justify-content-center mb-0">'

        // ← Anterior
        if (currentPage > 1) {
            w << "<li class=\"page-item\"><a class=\"page-link\" href=\"${link(currentPage - 1)}\">&laquo;</a></li>"
        } else {
            w << '<li class="page-item disabled"><span class="page-link">&laquo;</span></li>'
        }

        // Ventana de páginas: hasta 10 números deslizándose alrededor de la página actual
        int win      = 10
        int winStart = Math.max(1, currentPage - (int) (win / 2))
        int winEnd   = Math.min(totalPages, winStart + win - 1)
        winStart     = Math.max(1, winEnd - win + 1)

        if (winStart > 1) {
            w << "<li class=\"page-item\"><a class=\"page-link\" href=\"${link(1)}\">1</a></li>"
            if (winStart > 2) w << '<li class="page-item disabled"><span class="page-link">&hellip;</span></li>'
        }

        for (int p = winStart; p <= winEnd; p++) {
            if (p == currentPage) {
                w << "<li class=\"page-item active\"><span class=\"page-link\">${p}</span></li>"
            } else {
                w << "<li class=\"page-item\"><a class=\"page-link\" href=\"${link(p)}\">${p}</a></li>"
            }
        }

        if (winEnd < totalPages) {
            if (winEnd < totalPages - 1) w << '<li class="page-item disabled"><span class="page-link">&hellip;</span></li>'
            w << "<li class=\"page-item\"><a class=\"page-link\" href=\"${link(totalPages)}\">${totalPages}</a></li>"
        }

        // → Siguiente
        if (currentPage < totalPages) {
            w << "<li class=\"page-item\"><a class=\"page-link\" href=\"${link(currentPage + 1)}\">&raquo;</a></li>"
        } else {
            w << '<li class="page-item disabled"><span class="page-link">&raquo;</span></li>'
        }

        w << '</ul>'
    }
}
