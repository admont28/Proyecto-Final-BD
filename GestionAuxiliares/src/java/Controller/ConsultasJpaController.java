/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controller;

import Controller.exceptions.IllegalOrphanException;
import Controller.exceptions.NonexistentEntityException;
import Controller.exceptions.PreexistingEntityException;
import DTO.AuxiliaresPresentadosDTO;
import DTO.CumplimientoActividadesDTO;
import DTO.EvaluacionesDTO;
import DTO.HorarioAuxiliarDTO;
import DTO.SolicitudDTO;
import Entities.Area;
import Entities.Solicitante;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author DavidMontoya
 */
public class ConsultasJpaController implements Serializable {

    private EntityManagerFactory emf = null;
    PreparedStatement pst = null;
    
    public ConsultasJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    /**
     * Metodo que realiza una consulta SQL para saber
     * los auxiliares presentados a las convocatorias con sus distintas pruebas.
     * @return una lista de AuxiliaresPresentadosDTO para su facil acceso a los datos consultados.
     */
    public List<AuxiliaresPresentadosDTO> getAuxiliaresPresentados(){
        try {
            List<AuxiliaresPresentadosDTO> asDTO = new ArrayList<AuxiliaresPresentadosDTO>();
            Connection c= jdbcConnection.getInstance().obtenerConexion();
            String sql = "SELECT CON.CONVOCATORIA_ID, EVA.EVALUACION_AUXILIARES_ID, CON.FECHA_CREACION , AUX.NOMBRE_AUXILIAR, AUX.APELLIDO_AUXILIAR, SEL.ESTADO " +
                            "FROM SELECCION_AUXILIARES SEL, AUXILIAR AUX, CONVOCATORIA CON, EVALUACION_AUXILIARES EVA " +
                            "WHERE AUX.AUXILIAR_ID= SEL.ID_AUXILIARES " +
                            "AND SEL.ID_EVALUACION_AUXILIARES= EVA.EVALUACION_AUXILIARES_ID " +
                            "AND EVA.ID_CONVOCATORIA=CON.CONVOCATORIA_ID " +
                            "ORDER BY CON.CONVOCATORIA_ID ASC";
            pst = c.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                AuxiliaresPresentadosDTO a = new AuxiliaresPresentadosDTO(rs.getString("NOMBRE_AUXILIAR"), rs.getString("APELLIDO_AUXILIAR"), rs.getString("CONVOCATORIA_ID"), rs.getString("EVALUACION_AUXILIARES_ID"), rs.getShort("ESTADO"), rs.getDate("FECHA_CREACION"));
                asDTO.add(a);
            }
            pst.close();
            return asDTO;
//        EntityManager em = getEntityManager();
//        Query q = em.createQuery("SELECT new DTO.AuxiliaresPresentadosDTO(aux.NOMBRE_AUXILIAR, AUX.APELLIDO_AUXILIAR, CON.CONVOCATORIA_ID, EVA.EVALUACION_AUXILIARES_ID, SEL.ESTADO) " +
//                                "FROM SeleccionAuxiliares sel, Auxiliar aux, Convocatoria con, EvaluacionAuxiliares eva " +
//                                "WHERE aux.AUXILIAR_ID= SEL.ID_AUXILIARES " +
//                                "AND SEL.ID_EVALUACION_AUXILIARES= EVA.EVALUACION_AUXILIARES_ID " +
//                                "AND EVA.ID_CONVOCATORIA=CON.CONVOCATORIA_ID");
//        String convocatoria = "005";
//        Query q = em.createQuery("SELECT new DTO.AuxiliaresPresentadosDTO(AUX.nombreAuxiliar, AUX.apellidoAuxiliar, C.convocatoriaId, EVA.evaluacionAuxiliaresId, SEL.estado) FROM SeleccionAuxiliares sel, Auxiliar aux, EvaluacionAuxiliares eva, Convocatoria c WHERE EVA.idConvocatoria = c.convocatoriaId AND AUX.auxiliarId = SEL.seleccionAuxiliaresPK.idAuxiliares  AND SEL.seleccionAuxiliaresPK.idEvaluacionAuxiliares = EVA.evaluacionAuxiliaresId");
//        Query q = em.createQuery("SELECT new DTO.AuxiliaresPresentadosDTO(AUX.nombreAuxiliar, AUX.apellidoAuxiliar, aux.apellidoAuxiliar, EVA.evaluacionAuxiliaresId, SEL.estado) "
//                + "FROM SeleccionAuxiliares sel, Auxiliar aux, EvaluacionAuxiliares eva "
//                + "WHERE AUX.auxiliarId = SEL.seleccionAuxiliaresPK.idAuxiliares "
//                + "AND SEL.seleccionAuxiliaresPK.idEvaluacionAuxiliares = EVA.evaluacionAuxiliaresId");
//        return q.getResultList();
        } catch (SQLException ex) {
            Logger.getLogger(ConsultasJpaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    /**
     * Metodo que permite realizar una consulta SQL a la base de datos para
     * saber los promedios de las calificaciones agrupados por evaluación.
     * @return una lista de EvaluacionesDTO para su facil acceso a los datos consultados.
     */
    public List<EvaluacionesDTO> getPromedioEvaluaciones(){
        try {
            List<EvaluacionesDTO> peDTO = new ArrayList<EvaluacionesDTO>();
            Connection c = jdbcConnection.getInstance().obtenerConexion();
            String sql = "SELECT ROUND(AVG(SEL.CALIFICACION)) as PROMEDIO, EVA.EVALUACION_AUXILIARES_ID " +
                        "FROM AUXILIAR AUX, SELECCION_AUXILIARES SEL, EVALUACION_AUXILIARES EVA " +
                        "WHERE SEL.ID_AUXILIARES= AUX.AUXILIAR_ID " +
                        "AND SEL.ESTADO = 1 " +
                        "GROUP BY EVA.EVALUACION_AUXILIARES_ID";
            pst = c.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                EvaluacionesDTO p = new EvaluacionesDTO(rs.getString(2) ,rs.getShort(1));
                peDTO.add(p);
            }
            pst.close();
            return peDTO;
        } catch (SQLException ex) {
            Logger.getLogger(ConsultasJpaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    /**
     * Metodo que permite realizar una consulta SQL a la base de datos para saber
     * los auxiliares seleccionados con su respectiva evaluacion y calificacion.
     * @return retorna una lista de EvaluacionesDTO para su facil acceso a los datos consultados.
     */
    public List<EvaluacionesDTO> getAuxiliaresSeleccionados(){
        try {
            List<EvaluacionesDTO> peDTO = new ArrayList<EvaluacionesDTO>();
            Connection c = jdbcConnection.getInstance().obtenerConexion();
            String sql = "SELECT AUX.NOMBRE_AUXILIAR, AUX.APELLIDO_AUXILIAR, SEL.ID_EVALUACION_AUXILIARES, SEL.CALIFICACION, SEL.PROMEDIO " +
                        "FROM AUXILIAR AUX, SELECCION_AUXILIARES SEL " +
                        "WHERE SEL.ID_AUXILIARES=AUX.AUXILIAR_ID " +
                        "AND SEL.ESTADO=1 " +
                        "ORDER BY SEL.PROMEDIO DESC";
            pst = c.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                EvaluacionesDTO p = new EvaluacionesDTO(rs.getString("ID_EVALUACION_AUXILIARES"), rs.getDouble("PROMEDIO"), rs.getString("NOMBRE_AUXILIAR"), rs.getString("APELLIDO_AUXILIAR"), rs.getDouble("CALIFICACION"));
                peDTO.add(p);
            }
            pst.close();
            return peDTO;
        } catch (SQLException ex) {
            Logger.getLogger(ConsultasJpaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    /**
     * Metodo que permite realizar una consulta SQL a la base de datos para saber
     * las solicitudes hechas por cada solicitante con sus respectivos campos.
     * @return una lista de SolicitudesDTO para su facil acceso a los datos consultados.
     */
    public List<SolicitudDTO> getSolicitudes(){
        try {
            List<SolicitudDTO> sDTO = new ArrayList<SolicitudDTO>();
            Connection c = jdbcConnection.getInstance().obtenerConexion();
            String sql = "SELECT SOL.NOMBRE_SOLICITANTE, SOL.APELLIDO_SOLICITANTE, CAR.NOMBRE_CARGO, SOLA.DESC_SOLICITUD_AUXILIARES AS DESCRIPCION, SOLA.CANTIDAD_AUXILIARES, SOLA.CANTIDAD_HORAS, SOLAR.ID_SOLICITUD_AUXILIARES, REQ.DESCRIPCION_REQUISITO " +
                        "FROM SOLICITANTE SOL, SOLICITUD_AUXILIARES SOLA, SOL_AUXILIARES_REQUISITOS SOLAR, REQUISITO REQ, CARGO CAR " +
                        "WHERE SOLA.ID_SOLICITANTE=SOL.SOLICITANTE_ID " +
                        "AND SOLA.SOLICITUD_AUXILIARES_ID=SOLAR.ID_SOLICITUD_AUXILIARES " +
                        "AND SOL.ID_CARGO = CAR.CARGO_ID " +
                        "AND REQ.REQUISITO_ID=SOLAR.ID_REQUISITO";
            pst = c.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                SolicitudDTO s = new SolicitudDTO(rs.getString("NOMBRE_SOLICITANTE"), rs.getString("APELLIDO_SOLICITANTE"), rs.getString("NOMBRE_CARGO"), rs.getString("DESCRIPCION"), rs.getShort("CANTIDAD_AUXILIARES"), rs.getShort("CANTIDAD_HORAS"), rs.getString("ID_SOLICITUD_AUXILIARES"), rs.getString("DESCRIPCION_REQUISITO"));
                sDTO.add(s);
            }
            pst.close();
            return sDTO;
        } catch (SQLException ex) {
            Logger.getLogger(ConsultasJpaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    /**
     * Metodo que permite realizar una consulta SQL a la base de datos para saber
     * el horario de actividades de los auxiliares.
     * @return una lista de HorarioAuxiliarDTO para su facil acceso a los datos consultados.
     */
    public List<HorarioAuxiliarDTO> getHorarioAuxiliares(){
        try {
            List<HorarioAuxiliarDTO> hDTO = new ArrayList<HorarioAuxiliarDTO>();
            Connection c = jdbcConnection.getInstance().obtenerConexion();
            String sql = "SELECT AUX.NOMBRE_AUXILIAR, AUX.APELLIDO_AUXILIAR,  TIPO.DESCRIPCION_TIPO_AUXILIAR,  HORAC.HORARIO_ACTIVIDADES_ID, FEC.FECHA_FECHA_HORARIO, HORA.HORA_INICIO, HORA.HORA_FIN " +
                        "FROM AUXILIAR AUX, HORARIO_ACTIVIDADES HORAC, TIPO_AUXILIAR TIPO, FECHA_HORARIO FEC, HORA HORA " +
                        "WHERE AUX.AUXILIAR_ID =HORAC.ID_AUXILIAR " +
                        "AND HORAC.HORARIO_ACTIVIDADES_ID=FEC.ID_HORARIO_ACTIVIDADES " +
                        "AND FEC.FECHA_HORARIO_ID= HORA.ID_FECHA_HORARIO " +
                        "AND HORAC.ID_TIPO_AUXILIAR=TIPO.TIPO_AUXILIAR_ID " +
                        "ORDER BY HORAC.HORARIO_ACTIVIDADES_ID ASC";
            pst = c.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                HorarioAuxiliarDTO h = new HorarioAuxiliarDTO(rs.getString("NOMBRE_AUXILIAR"), rs.getString("APELLIDO_AUXILIAR"), rs.getString("DESCRIPCION_TIPO_AUXILIAR"), rs.getString("HORARIO_ACTIVIDADES_ID"), rs.getDate("FECHA_FECHA_HORARIO"), rs.getString("HORA_INICIO"), rs.getString("HORA_FIN"));
                hDTO.add(h);
            }
            pst.close();
            return hDTO;
        } catch (SQLException ex) {
            Logger.getLogger(ConsultasJpaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    /**
     * Metodo que permite realizar una consulta SQL a la base de datos para saber
     * que auxiliares cumplieron las actividades de monitoria.
     * @return una lista de cumplimientoActividadesDTO para su facil acceso a los datos consultados.
     */
    public List<CumplimientoActividadesDTO> getAuxiliaresCumplieron(){
        try {
            List<CumplimientoActividadesDTO> caDTO = new ArrayList<CumplimientoActividadesDTO>();
            Connection c = jdbcConnection.getInstance().obtenerConexion();
            String sql = "SELECT AUX.NOMBRE_AUXILIAR, AUX.APELLIDO_AUXILIAR, CUM.DESC_CUMP_ACT AS DESCRIPCION " +
                        "FROM CUMPLIMIENTO_ACTIVIDADES CUM, AUXILIAR AUX, AUX_CUMPLIMIENTO_ACTIVIDADES AUXCUM, SOLICITANTE SOL " +
                        "WHERE AUX.AUXILIAR_ID = AUXCUM.ID_AUXILIAR " +
                        "AND AUXCUM.ID_CUMPLIMIENTO_ACTIVIDADES=CUM.CUMP_ACT_ID " +
                        "AND SOL.SOLICITANTE_ID = CUM.ID_SOLICITANTE";
            pst = c.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                CumplimientoActividadesDTO ca = new CumplimientoActividadesDTO(rs.getString("NOMBRE_AUXILIAR"), rs.getString("APELLIDO_AUXILIAR"), rs.getString("DESCRIPCION"));
                caDTO.add(ca);
            }
            pst.close();
            return caDTO;
        } catch (SQLException ex) {
            Logger.getLogger(ConsultasJpaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    /**
     * Metodo que permite realizar una consulta SQL a la base de datos para saber
     * cuantos auxiliares han pedido en total cada solicitante.
     * @return una lista de SolicitudDTO para su facil acceso a los datos consultados.
     */
    public List<SolicitudDTO> getTotalidadAuxiliaresSolicitados(){
        try {
            List<SolicitudDTO> sDTO = new ArrayList<SolicitudDTO>();
            Connection c = jdbcConnection.getInstance().obtenerConexion();
            String sql = "SELECT SOL.SOLICITANTE_ID, SOL.NOMBRE_SOLICITANTE, SUM(SOLA.CANTIDAD_AUXILIARES) " +
                        "FROM SOLICITANTE SOL, SOLICITUD_AUXILIARES SOLA, SOL_AUXILIARES_REQUISITOS SOLAR " +
                        "WHERE SOLA.ID_SOLICITANTE=SOL.SOLICITANTE_ID " +
                        "AND SOLA.SOLICITUD_AUXILIARES_ID=SOLAR.ID_SOLICITUD_AUXILIARES " +
                        "GROUP BY SOL.SOLICITANTE_ID, SOL.NOMBRE_SOLICITANTE " +
                        "ORDER BY SUM(SOLA.CANTIDAD_AUXILIARES) DESC";
            pst = c.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                SolicitudDTO s = new SolicitudDTO(rs.getString("SOLICITANTE_ID"), rs.getString("NOMBRE_SOLICITANTE"), rs.getShort(3));
                sDTO.add(s);
            }
            pst.close();
            return sDTO;
        } catch (SQLException ex) {
            Logger.getLogger(ConsultasJpaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
}
