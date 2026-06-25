**PLANIFICACIÓN DEL MÓDULO DE LIQUIDACIÓN DE COMPLEJOS**

Vamos a ingresar en el módulo más importante de la app, la liquidación mediante el módulo LiquidacionDeComplejo. Vamos a realizar la planificación para mejorar la implementación actual tanto en precisión como en estética.
Detalles a considerar:
- Solo existe una merma que es por defecto 1 (1%). Por éso se lo ocultó en ControlCalidadComplejo.
- Se debe considerar que las leyes para la liquidación pueden negociarse. Por lo que debe utilizarse los porcentajes finales para ingresar manualmente estos datos mostrando adjunto la diferencia respecto a los valores registrados en ControlCalidadComplejo para el lote relacionado.
- Si el Cliente tiene por su parte valores de leyes para su lote, permitir el promediado para determinar los porcentajes finales.
- Se calculará el Valor Bruto de Venta (VBV) para zinc, plomo y plata de forma individual y en dólares americanos y bolivianos. Además debe obtener el total para ambas monedas.
- Se calculará la Regalía Minera (RM) para zinc, plomo y plata de forma individual y en dólares americanos y bolivianos. Además debe obtener el total para ambas monedas.
- El Valor por Tonelada (VPT) podrá determinarse de dos maneras a elección del usuario: manual, cuando el usuario ingresa el dato manualmente, y automático, cuando se lo calcula mediante cualquiera de los modos: por tablas (TablaOrigenCotizacionesComplejo) o contrato (TerminosDeContrato). Para todos los casos, cuando se establece un valor por tonelada deben recalcularse todos los campos relacioandos.
- Para el cálculo del Valor por Tonelada (VPT) cuando su cálculo sea automático, de inicio implementar en controllers de TablaOrigenCotizacionesComplejo y TerminosDeContrato funciones que reciban una referencia a RecepcionDeComplejo, porcentajes de ley de zinc, plomo y plata y respectivas referencias a TablaOrigenCotizacionesComplejo y TerminosDeContrato y que devuelvan valores aleatorios. Luego se implementará la lógica de estos módulos.
- Deben calcularse las retenciones según el detalle de retenciones de la Empresa relacionada al registro de RecepcionDeComplejo a liquidarse. Se debe permitir la eliminación de una o varias de ellas durante la liquidación.
- Si un lote (registro de RecepcionDeComplejo) tiene relación con un Anticipo y este anticipo solo está relacionado con un lote, realizar el descuento del total por pagar. Si no, permitir descontar una fracción del total por pagar para pagar el saldo en la liquidación de los demás lotes.
- Una liquidación podría producir un saldo negativo o en contra para el cliente. Analizar la relación que podría haber con el estado de cuenta, los anticipos contra futura entrega y las amortizaciones y elegir la forma de manejo más apropiada.
- Tomar en cuenta el archivo liquidacion_complejo.xlsx y la hoja COMPLEJO como referencia de cálculo.
- Alinear el diseño según lo trabajado hasta ahora y para la tabla de retenciones utilizar estilos de AdminLTE y Bootstrap.