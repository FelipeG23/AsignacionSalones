
CREATE OR REPLACE PROCEDURE CLASE.PR_ASIGNACION_AUTOMATICA(
                            p_idPrograma        IN      NUMBER,
                            p_traza             OUT     VARCHAR2
)
AS
     v_salonDisponible         VARCHAR2(1000) :='';
     
     --
     --CURSOR PARA BUSCAR LOS GRUPOS ASOCIADOS A UN PROGRAMA
     --
     CURSOR C_BUSCAR_GRUPOS IS
        SELECT DISTINCT GRUPO.CODIGO
            FROM PROGRAMAS PROGRAMA, GRUPOS GRUPO, HORARIOS HORARIO
        WHERE     PROGRAMA.CODIGO = GRUPO.PL_CODIGO
       AND GRUPO.CODIGO = HORARIO.GRU_CODIGO
       AND PROGRAMA.CODIGO = p_idPrograma
       ; 
        
     --
     --CURSOR PARA BUSCAR LOS SALONES 
     --
     CURSOR C_BUS_SALON_DIS (v_Grupo IN varchar2) IS 
     SELECT CODIGO  FROM SALONES
        WHERE CODIGO NOT IN(
        SELECT SAL_CODIGO FROM HORARIOS
                WHERE FECHA IN (SELECT HORARIO.FECHA  FROM HORARIOS HORARIO WHERE GRU_CODIGO = v_Grupo)
                AND TO_CHAR(HORA_INICIO,'HH12 PM')IN (SELECT TO_CHAR (HORARIO.HORA_INICIO, 'HH12 PM') FROM HORARIOS HORARIO WHERE GRU_CODIGO = v_Grupo)
                AND TO_CHAR(HORA_FIN,'HH12 PM') IN (SELECT TO_CHAR (HORARIO.HORA_FIN, 'HH12 PM') FROM HORARIOS HORARIO WHERE GRU_CODIGO = v_Grupo)
                AND SAL_CODIGO IS NOT NULL
        )
        AND ROWNUM<2
     ;
     
     --
     --CURSOR PARA BUSCAR LAS FECHAS SEGUN EL GRUPO
     --
     CURSOR C_BUSCARFECHAS (v_Grupo IN varchar2) IS 
     SELECT HORARIO.FECHA,
      HORARIO.HORA_INICIO INICIO,
      HORARIO.HORA_FIN    FIN
        FROM HORARIOS HORARIO
    WHERE GRU_CODIGO = v_Grupo
     ;
BEGIN
    
    
    
    
    --
    --SE RECORREN TODOS LOS GRUPOS DEL PROGRAMA
    --
    FOR GRUPO IN C_BUSCAR_GRUPOS LOOP
        
        
        ---
        --- BUSCO EL SALON DISPONIBLE
        ---
        
        OPEN C_BUS_SALON_DIS (GRUPO.CODIGO);
        FETCH C_BUS_SALON_DIS INTO v_salonDisponible;
        CLOSE C_BUS_SALON_DIS;
        
        
        FOR ITEM IN C_BUSCARFECHAS(GRUPO.CODIGO)  LOOP
            
            --
            -- ACTUALIZO CADA FECHA SEGUN EL GRUPO
            --
            
            UPDATE CLASE.HORARIOS
                SET SAL_CODIGO = v_salonDisponible
                WHERE GRU_CODIGO = GRUPO.CODIGO AND
                    FECHA = ITEM.FECHA AND
                    HORA_INICIO = ITEM.INICIO AND
                    HORA_FIN = ITEM.FIN
             ;
            
        END LOOP;
        --
        --
    END LOOP;    
    
    p_traza          := 'OK';
    
    
    EXCEPTION 
        WHEN OTHERS THEN       
     p_traza := 'Error ' || sqlerrm;

END;
/