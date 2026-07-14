-- F0 — Migración compósito (Conjuntos de Lotes)
-- Ejecutar en dev (127.0.0.1/liquidacion_demo) ANTES de arrancar la app con el dominio F1.
-- Contexto: 107 headers, 48593 detalle. Gotcha DDL: dbCreate:update NO relaja NOT NULL ni
-- agrega columnas NOT NULL a tablas con filas; por eso las nuevas van NULLABLE y aquí se
-- relajan las columnas de aprobación (D8) que el dominio deja de poblar.

-- 1) Columnas nuevas (nullable). Hibernate también las agregaría, pero las dejamos explícitas.
ALTER TABLE reporte_composito_de_lotes ADD COLUMN gestion_minera DATETIME NULL;
ALTER TABLE reporte_composito_de_lotes ADD COLUMN total_liquido_pagable DECIMAL(19,2) NULL;
ALTER TABLE composito_de_lotes_detalle  ADD COLUMN liquido_pagable DECIMAL(19,2) NULL;

-- 2) Relajar NOT NULL de columnas de aprobación (se retiran del dominio en F1/D8).
--    Sin esto, al quitar los campos del dominio los INSERT fallarían (columna NOT NULL sin default).
ALTER TABLE reporte_composito_de_lotes MODIFY estado_de_aprobacion VARCHAR(9) NULL;
ALTER TABLE reporte_composito_de_lotes MODIFY aprobado_por VARCHAR(255) NULL;

-- 3) (F4) fechaInicial/fechaFinal pasan a filtros OPCIONALES del header.
ALTER TABLE reporte_composito_de_lotes MODIFY fecha_inicial DATETIME NULL;
ALTER TABLE reporte_composito_de_lotes MODIFY fecha_final   DATETIME NULL;

-- 4) (F5) baja lógica del compósito (reabrir/anular, D7).
ALTER TABLE reporte_composito_de_lotes ADD COLUMN anulado BIT(1) NULL;
UPDATE reporte_composito_de_lotes SET anulado = 0 WHERE anulado IS NULL;
