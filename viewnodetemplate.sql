/*view query definition:
viewnodetemplate(ID, MAXINSTANCES, MININSTANCES, TYP, PROVIDER, INSTANCETYPE, BASEIMAGE, PACKAGES, CID, CIDTYPE) :- p_0(ID, MAXINSTANCES, MININSTANCES, TYP, PROVIDER, INSTANCETYPE, BASEIMAGE, PACKAGES, CID, CIDTYPE).
p_0(ID, MAXINSTANCES, MININSTANCES, TYP, PROVIDER, INSTANCETYPE, BASEIMAGE, PACKAGES, CID, CIDTYPE) :- TYP = 'os' , nodetemplate(ID, MAXINSTANCES, MININSTANCES, TYP, PROVIDER, INSTANCETYPE, BASEIMAGE, PACKAGES, COL8, COL9, COL10, COL11, CID, CIDTYPE, COL14, COL15, COL16, COL17).
*/
CREATE OR REPLACE VIEW public.viewnodetemplate AS 
SELECT __dummy__.COL0 AS ID,__dummy__.COL1 AS MAXINSTANCES,__dummy__.COL2 AS MININSTANCES,__dummy__.COL3 AS TYP,__dummy__.COL4 AS PROVIDER,__dummy__.COL5 AS INSTANCETYPE,__dummy__.COL6 AS BASEIMAGE,__dummy__.COL7 AS PACKAGES,__dummy__.COL8 AS CID,__dummy__.COL9 AS CIDTYPE 
FROM (SELECT DISTINCT viewnodetemplate_a10_0.COL0 AS COL0, viewnodetemplate_a10_0.COL1 AS COL1, viewnodetemplate_a10_0.COL2 AS COL2, viewnodetemplate_a10_0.COL3 AS COL3, viewnodetemplate_a10_0.COL4 AS COL4, viewnodetemplate_a10_0.COL5 AS COL5, viewnodetemplate_a10_0.COL6 AS COL6, viewnodetemplate_a10_0.COL7 AS COL7, viewnodetemplate_a10_0.COL8 AS COL8, viewnodetemplate_a10_0.COL9 AS COL9 
FROM (SELECT DISTINCT p_0_a10_0.COL0 AS COL0, p_0_a10_0.COL1 AS COL1, p_0_a10_0.COL2 AS COL2, p_0_a10_0.COL3 AS COL3, p_0_a10_0.COL4 AS COL4, p_0_a10_0.COL5 AS COL5, p_0_a10_0.COL6 AS COL6, p_0_a10_0.COL7 AS COL7, p_0_a10_0.COL8 AS COL8, p_0_a10_0.COL9 AS COL9 
FROM (SELECT DISTINCT nodetemplate_a18_0.ID AS COL0, nodetemplate_a18_0.MAXINSTANCES AS COL1, nodetemplate_a18_0.MININSTANCES AS COL2, nodetemplate_a18_0.TYP AS COL3, nodetemplate_a18_0.PROVIDER AS COL4, nodetemplate_a18_0.INSTANCETYPE AS COL5, nodetemplate_a18_0.BASEIMAGE AS COL6, nodetemplate_a18_0.PACKAGES AS COL7, nodetemplate_a18_0.CID AS COL8, nodetemplate_a18_0.CIDTYPE AS COL9 
FROM public.nodetemplate AS nodetemplate_a18_0 
WHERE nodetemplate_a18_0.TYP = 'os' ) AS p_0_a10_0  ) AS viewnodetemplate_a10_0  ) AS __dummy__;

DROP MATERIALIZED VIEW IF EXISTS public.__dummy__materialized_viewnodetemplate;

CREATE  MATERIALIZED VIEW public.__dummy__materialized_viewnodetemplate AS 
SELECT * FROM public.viewnodetemplate;

CREATE EXTENSION IF NOT EXISTS plsh;

CREATE OR REPLACE FUNCTION public.viewnodetemplate_run_shell(text) RETURNS text AS $$
#!/bin/sh
echo "true"
$$ LANGUAGE plsh;
CREATE OR REPLACE FUNCTION public.viewnodetemplate_detect_update()
RETURNS trigger
LANGUAGE plpgsql
SECURITY DEFINER
AS $$
  DECLARE
  text_var1 text;
  text_var2 text;
  text_var3 text;
  func text;
  tv text;
  deletion_data text;
  insertion_data text;
  json_data text;
  result text;
  user_name text;
  BEGIN
  IF NOT EXISTS (SELECT * FROM information_schema.tables WHERE table_name = 'viewnodetemplate_delta_action_flag') THEN
    insertion_data := (SELECT (array_to_json(array_agg(t)))::text FROM (SELECT * FROM public.viewnodetemplate EXCEPT SELECT * FROM public.__dummy__materialized_viewnodetemplate) as t);
    IF insertion_data IS NOT DISTINCT FROM NULL THEN 
        insertion_data := '[]';
    END IF; 
    deletion_data := (SELECT (array_to_json(array_agg(t)))::text FROM (SELECT * FROM public.__dummy__materialized_viewnodetemplate EXCEPT SELECT * FROM public.viewnodetemplate) as t);
    IF deletion_data IS NOT DISTINCT FROM NULL THEN 
        deletion_data := '[]';
    END IF; 
    IF (insertion_data IS DISTINCT FROM '[]') OR (insertion_data IS DISTINCT FROM '[]') THEN 
        user_name := (SELECT session_user);
        IF NOT (user_name = 'dejima') THEN 
            json_data := concat('{"view": ' , '"public.viewnodetemplate"', ', ' , '"insertions": ' , insertion_data , ', ' , '"deletions": ' , deletion_data , '}');
            result := public.viewnodetemplate_run_shell(json_data);
            IF result = 'true' THEN 
                REFRESH MATERIALIZED VIEW public.__dummy__materialized_viewnodetemplate;
                FOR func IN (select distinct trigger_schema||'.non_trigger_'||substring(action_statement, 19) as function 
                from information_schema.triggers where trigger_schema = 'public' and event_object_table='viewnodetemplate'
                and action_timing='AFTER' and (event_manipulation='INSERT' or event_manipulation='DELETE' or event_manipulation='UPDATE')
                and action_statement like 'EXECUTE PROCEDURE %') 
                LOOP
                    EXECUTE 'SELECT ' || func into tv;
                END LOOP;
            ELSE
                -- RAISE LOG 'result from running the sh script: %', result;
                RAISE check_violation USING MESSAGE = 'update on view is rejected by the external tool, result from running the sh script: ' 
                || result;
            END IF;
        ELSE 
            RAISE LOG 'function of detecting dejima update is called by % , no request sent to dejima proxy', user_name;
        END IF;
    END IF;
  END IF;
  RETURN NULL;
  EXCEPTION
    WHEN object_not_in_prerequisite_state THEN
        RAISE object_not_in_prerequisite_state USING MESSAGE = 'no permission to insert or delete or update to source relations of public.viewnodetemplate';
    WHEN OTHERS THEN
        GET STACKED DIAGNOSTICS text_var1 = RETURNED_SQLSTATE,
                                text_var2 = PG_EXCEPTION_DETAIL,
                                text_var3 = MESSAGE_TEXT;
        RAISE SQLSTATE 'DA000' USING MESSAGE = 'error on the function public.viewnodetemplate_detect_update() ; error code: ' || text_var1 || ' ; ' || text_var2 ||' ; ' || text_var3;
        RETURN NULL;
  END;
$$;

CREATE OR REPLACE FUNCTION public.non_trigger_viewnodetemplate_detect_update()
RETURNS text 
LANGUAGE plpgsql
SECURITY DEFINER
AS $$
  DECLARE
  text_var1 text;
  text_var2 text;
  text_var3 text;
  func text;
  tv text;
  deletion_data text;
  insertion_data text;
  json_data text;
  result text;
  user_name text;
  BEGIN
  IF NOT EXISTS (SELECT * FROM information_schema.tables WHERE table_name = 'viewnodetemplate_delta_action_flag') THEN
    insertion_data := (SELECT (array_to_json(array_agg(t)))::text FROM (SELECT * FROM public.viewnodetemplate EXCEPT SELECT * FROM public.__dummy__materialized_viewnodetemplate) as t);
    IF insertion_data IS NOT DISTINCT FROM NULL THEN 
        insertion_data := '[]';
    END IF; 
    deletion_data := (SELECT (array_to_json(array_agg(t)))::text FROM (SELECT * FROM public.__dummy__materialized_viewnodetemplate EXCEPT SELECT * FROM public.viewnodetemplate) as t);
    IF deletion_data IS NOT DISTINCT FROM NULL THEN 
        deletion_data := '[]';
    END IF; 
    IF (insertion_data IS DISTINCT FROM '[]') OR (insertion_data IS DISTINCT FROM '[]') THEN 
        user_name := (SELECT session_user);
        IF NOT (user_name = 'dejima') THEN 
            json_data := concat('{"view": ' , '"public.viewnodetemplate"', ', ' , '"insertions": ' , insertion_data , ', ' , '"deletions": ' , deletion_data , '}');
            result := public.viewnodetemplate_run_shell(json_data);
            IF result = 'true' THEN 
                REFRESH MATERIALIZED VIEW public.__dummy__materialized_viewnodetemplate;
                FOR func IN (select distinct trigger_schema||'.non_trigger_'||substring(action_statement, 19) as function 
                from information_schema.triggers where trigger_schema = 'public' and event_object_table='viewnodetemplate'
                and action_timing='AFTER' and (event_manipulation='INSERT' or event_manipulation='DELETE' or event_manipulation='UPDATE')
                and action_statement like 'EXECUTE PROCEDURE %') 
                LOOP
                    EXECUTE 'SELECT ' || func into tv;
                END LOOP;
            ELSE
                -- RAISE LOG 'result from running the sh script: %', result;
                RAISE check_violation USING MESSAGE = 'update on view is rejected by the external tool, result from running the sh script: ' 
                || result;
            END IF;
        ELSE 
            RAISE LOG 'function of detecting dejima update is called by % , no request sent to dejima proxy', user_name;
        END IF;
    END IF;
  END IF;
  RETURN NULL;
  EXCEPTION
    WHEN object_not_in_prerequisite_state THEN
        RAISE object_not_in_prerequisite_state USING MESSAGE = 'no permission to insert or delete or update to source relations of public.viewnodetemplate';
    WHEN OTHERS THEN
        GET STACKED DIAGNOSTICS text_var1 = RETURNED_SQLSTATE,
                                text_var2 = PG_EXCEPTION_DETAIL,
                                text_var3 = MESSAGE_TEXT;
        RAISE SQLSTATE 'DA000' USING MESSAGE = 'error on the function public.viewnodetemplate_detect_update() ; error code: ' || text_var1 || ' ; ' || text_var2 ||' ; ' || text_var3;
        RETURN NULL;
  END;
$$;

DROP TRIGGER IF EXISTS nodetemplate_detect_update_viewnodetemplate ON public.nodetemplate;
        CREATE TRIGGER nodetemplate_detect_update_viewnodetemplate
            AFTER INSERT OR UPDATE OR DELETE ON
            public.nodetemplate FOR EACH STATEMENT EXECUTE PROCEDURE public.viewnodetemplate_detect_update();

CREATE OR REPLACE FUNCTION public.viewnodetemplate_delta_action()
RETURNS TRIGGER
LANGUAGE plpgsql
SECURITY DEFINER
AS $$
  DECLARE
  text_var1 text;
  text_var2 text;
  text_var3 text;
  deletion_data text;
  insertion_data text;
  json_data text;
  result text;
  user_name text;
  temprecΔ_del_nodetemplate public.nodetemplate%ROWTYPE;
temprecΔ_ins_nodetemplate public.nodetemplate%ROWTYPE;
  BEGIN
    IF NOT EXISTS (SELECT * FROM information_schema.tables WHERE table_name = 'viewnodetemplate_delta_action_flag') THEN
        -- RAISE LOG 'execute procedure viewnodetemplate_delta_action';
        CREATE TEMPORARY TABLE viewnodetemplate_delta_action_flag ON COMMIT DROP AS (SELECT true as finish);
        IF EXISTS (SELECT  
FROM (SELECT  
FROM (SELECT  
FROM (SELECT DISTINCT __dummy__materialized_viewnodetemplate_a10_0.ID AS COL0, __dummy__materialized_viewnodetemplate_a10_0.MAXINSTANCES AS COL1, __dummy__materialized_viewnodetemplate_a10_0.MININSTANCES AS COL2, __dummy__materialized_viewnodetemplate_a10_0.TYP AS COL3, __dummy__materialized_viewnodetemplate_a10_0.PROVIDER AS COL4, __dummy__materialized_viewnodetemplate_a10_0.INSTANCETYPE AS COL5, __dummy__materialized_viewnodetemplate_a10_0.BASEIMAGE AS COL6, __dummy__materialized_viewnodetemplate_a10_0.PACKAGES AS COL7, __dummy__materialized_viewnodetemplate_a10_0.CID AS COL8, __dummy__materialized_viewnodetemplate_a10_0.CIDTYPE AS COL9 
FROM public.__dummy__materialized_viewnodetemplate AS __dummy__materialized_viewnodetemplate_a10_0 
WHERE NOT EXISTS ( SELECT * 
FROM __temp__Δ_del_viewnodetemplate AS __temp__Δ_del_viewnodetemplate_a10 
WHERE __temp__Δ_del_viewnodetemplate_a10.CIDTYPE IS NOT DISTINCT FROM __dummy__materialized_viewnodetemplate_a10_0.CIDTYPE AND __temp__Δ_del_viewnodetemplate_a10.CID IS NOT DISTINCT FROM __dummy__materialized_viewnodetemplate_a10_0.CID AND __temp__Δ_del_viewnodetemplate_a10.PACKAGES IS NOT DISTINCT FROM __dummy__materialized_viewnodetemplate_a10_0.PACKAGES AND __temp__Δ_del_viewnodetemplate_a10.BASEIMAGE IS NOT DISTINCT FROM __dummy__materialized_viewnodetemplate_a10_0.BASEIMAGE AND __temp__Δ_del_viewnodetemplate_a10.INSTANCETYPE IS NOT DISTINCT FROM __dummy__materialized_viewnodetemplate_a10_0.INSTANCETYPE AND __temp__Δ_del_viewnodetemplate_a10.PROVIDER IS NOT DISTINCT FROM __dummy__materialized_viewnodetemplate_a10_0.PROVIDER AND __temp__Δ_del_viewnodetemplate_a10.TYP IS NOT DISTINCT FROM __dummy__materialized_viewnodetemplate_a10_0.TYP AND __temp__Δ_del_viewnodetemplate_a10.MININSTANCES IS NOT DISTINCT FROM __dummy__materialized_viewnodetemplate_a10_0.MININSTANCES AND __temp__Δ_del_viewnodetemplate_a10.MAXINSTANCES IS NOT DISTINCT FROM __dummy__materialized_viewnodetemplate_a10_0.MAXINSTANCES AND __temp__Δ_del_viewnodetemplate_a10.ID IS NOT DISTINCT FROM __dummy__materialized_viewnodetemplate_a10_0.ID )  UNION SELECT DISTINCT __temp__Δ_ins_viewnodetemplate_a10_0.ID AS COL0, __temp__Δ_ins_viewnodetemplate_a10_0.MAXINSTANCES AS COL1, __temp__Δ_ins_viewnodetemplate_a10_0.MININSTANCES AS COL2, __temp__Δ_ins_viewnodetemplate_a10_0.TYP AS COL3, __temp__Δ_ins_viewnodetemplate_a10_0.PROVIDER AS COL4, __temp__Δ_ins_viewnodetemplate_a10_0.INSTANCETYPE AS COL5, __temp__Δ_ins_viewnodetemplate_a10_0.BASEIMAGE AS COL6, __temp__Δ_ins_viewnodetemplate_a10_0.PACKAGES AS COL7, __temp__Δ_ins_viewnodetemplate_a10_0.CID AS COL8, __temp__Δ_ins_viewnodetemplate_a10_0.CIDTYPE AS COL9 
FROM __temp__Δ_ins_viewnodetemplate AS __temp__Δ_ins_viewnodetemplate_a10_0  ) AS viewnodetemplate_a10_0 
WHERE viewnodetemplate_a10_0.COL3  IS DISTINCT FROM  'os'  UNION ALL SELECT  
FROM (SELECT DISTINCT __dummy__materialized_viewnodetemplate_a10_0.ID AS COL0, __dummy__materialized_viewnodetemplate_a10_0.MAXINSTANCES AS COL1, __dummy__materialized_viewnodetemplate_a10_0.MININSTANCES AS COL2, __dummy__materialized_viewnodetemplate_a10_0.TYP AS COL3, __dummy__materialized_viewnodetemplate_a10_0.PROVIDER AS COL4, __dummy__materialized_viewnodetemplate_a10_0.INSTANCETYPE AS COL5, __dummy__materialized_viewnodetemplate_a10_0.BASEIMAGE AS COL6, __dummy__materialized_viewnodetemplate_a10_0.PACKAGES AS COL7, __dummy__materialized_viewnodetemplate_a10_0.CID AS COL8, __dummy__materialized_viewnodetemplate_a10_0.CIDTYPE AS COL9 
FROM public.__dummy__materialized_viewnodetemplate AS __dummy__materialized_viewnodetemplate_a10_0 
WHERE NOT EXISTS ( SELECT * 
FROM __temp__Δ_del_viewnodetemplate AS __temp__Δ_del_viewnodetemplate_a10 
WHERE __temp__Δ_del_viewnodetemplate_a10.CIDTYPE IS NOT DISTINCT FROM __dummy__materialized_viewnodetemplate_a10_0.CIDTYPE AND __temp__Δ_del_viewnodetemplate_a10.CID IS NOT DISTINCT FROM __dummy__materialized_viewnodetemplate_a10_0.CID AND __temp__Δ_del_viewnodetemplate_a10.PACKAGES IS NOT DISTINCT FROM __dummy__materialized_viewnodetemplate_a10_0.PACKAGES AND __temp__Δ_del_viewnodetemplate_a10.BASEIMAGE IS NOT DISTINCT FROM __dummy__materialized_viewnodetemplate_a10_0.BASEIMAGE AND __temp__Δ_del_viewnodetemplate_a10.INSTANCETYPE IS NOT DISTINCT FROM __dummy__materialized_viewnodetemplate_a10_0.INSTANCETYPE AND __temp__Δ_del_viewnodetemplate_a10.PROVIDER IS NOT DISTINCT FROM __dummy__materialized_viewnodetemplate_a10_0.PROVIDER AND __temp__Δ_del_viewnodetemplate_a10.TYP IS NOT DISTINCT FROM __dummy__materialized_viewnodetemplate_a10_0.TYP AND __temp__Δ_del_viewnodetemplate_a10.MININSTANCES IS NOT DISTINCT FROM __dummy__materialized_viewnodetemplate_a10_0.MININSTANCES AND __temp__Δ_del_viewnodetemplate_a10.MAXINSTANCES IS NOT DISTINCT FROM __dummy__materialized_viewnodetemplate_a10_0.MAXINSTANCES AND __temp__Δ_del_viewnodetemplate_a10.ID IS NOT DISTINCT FROM __dummy__materialized_viewnodetemplate_a10_0.ID )  UNION SELECT DISTINCT __temp__Δ_ins_viewnodetemplate_a10_0.ID AS COL0, __temp__Δ_ins_viewnodetemplate_a10_0.MAXINSTANCES AS COL1, __temp__Δ_ins_viewnodetemplate_a10_0.MININSTANCES AS COL2, __temp__Δ_ins_viewnodetemplate_a10_0.TYP AS COL3, __temp__Δ_ins_viewnodetemplate_a10_0.PROVIDER AS COL4, __temp__Δ_ins_viewnodetemplate_a10_0.INSTANCETYPE AS COL5, __temp__Δ_ins_viewnodetemplate_a10_0.BASEIMAGE AS COL6, __temp__Δ_ins_viewnodetemplate_a10_0.PACKAGES AS COL7, __temp__Δ_ins_viewnodetemplate_a10_0.CID AS COL8, __temp__Δ_ins_viewnodetemplate_a10_0.CIDTYPE AS COL9 
FROM __temp__Δ_ins_viewnodetemplate AS __temp__Δ_ins_viewnodetemplate_a10_0  ) AS viewnodetemplate_a10_0 
WHERE NOT EXISTS ( SELECT * 
FROM public.nodetemplate AS nodetemplate_a18 
WHERE nodetemplate_a18.ID IS NOT DISTINCT FROM viewnodetemplate_a10_0.COL0 ) ) AS ⊥_a0_0  ) AS __dummy__ )
        THEN 
          RAISE check_violation USING MESSAGE = 'Invalid update on view';
        END IF;
        CREATE TEMPORARY TABLE Δ_del_nodetemplate WITH OIDS ON COMMIT DROP AS SELECT (ROW(COL0,COL1,COL2,COL3,COL4,COL5,COL6,COL7,COL8,COL9,COL10,COL11,COL12,COL13,COL14,COL15,COL16,COL17) :: public.nodetemplate).* 
            FROM (SELECT DISTINCT Δ_del_nodetemplate_a18_0.COL0 AS COL0, Δ_del_nodetemplate_a18_0.COL1 AS COL1, Δ_del_nodetemplate_a18_0.COL2 AS COL2, Δ_del_nodetemplate_a18_0.COL3 AS COL3, Δ_del_nodetemplate_a18_0.COL4 AS COL4, Δ_del_nodetemplate_a18_0.COL5 AS COL5, Δ_del_nodetemplate_a18_0.COL6 AS COL6, Δ_del_nodetemplate_a18_0.COL7 AS COL7, Δ_del_nodetemplate_a18_0.COL8 AS COL8, Δ_del_nodetemplate_a18_0.COL9 AS COL9, Δ_del_nodetemplate_a18_0.COL10 AS COL10, Δ_del_nodetemplate_a18_0.COL11 AS COL11, Δ_del_nodetemplate_a18_0.COL12 AS COL12, Δ_del_nodetemplate_a18_0.COL13 AS COL13, Δ_del_nodetemplate_a18_0.COL14 AS COL14, Δ_del_nodetemplate_a18_0.COL15 AS COL15, Δ_del_nodetemplate_a18_0.COL16 AS COL16, Δ_del_nodetemplate_a18_0.COL17 AS COL17 
FROM (SELECT DISTINCT nodetemplate_a18_0.ID AS COL0, nodetemplate_a18_0.MAXINSTANCES AS COL1, nodetemplate_a18_0.MININSTANCES AS COL2, nodetemplate_a18_0.TYP AS COL3, nodetemplate_a18_0.PROVIDER AS COL4, nodetemplate_a18_0.INSTANCETYPE AS COL5, nodetemplate_a18_0.BASEIMAGE AS COL6, nodetemplate_a18_0.PACKAGES AS COL7, nodetemplate_a18_0.RID1 AS COL8, nodetemplate_a18_0.RID1TYPE AS COL9, nodetemplate_a18_0.RID2 AS COL10, nodetemplate_a18_0.RID2TYPE AS COL11, nodetemplate_a18_0.CID AS COL12, nodetemplate_a18_0.CIDTYPE AS COL13, nodetemplate_a18_0.DANAME AS COL14, nodetemplate_a18_0.XMLNS AS COL15, nodetemplate_a18_0.ARTIFACTREF AS COL16, nodetemplate_a18_0.ARTIFACTTYPE AS COL17 
FROM public.nodetemplate AS nodetemplate_a18_0 
WHERE nodetemplate_a18_0.TYP = 'os' AND NOT EXISTS ( SELECT * 
FROM (SELECT DISTINCT __dummy__materialized_viewnodetemplate_a10_0.ID AS COL0, __dummy__materialized_viewnodetemplate_a10_0.MAXINSTANCES AS COL1, __dummy__materialized_viewnodetemplate_a10_0.MININSTANCES AS COL2, __dummy__materialized_viewnodetemplate_a10_0.TYP AS COL3, __dummy__materialized_viewnodetemplate_a10_0.PROVIDER AS COL4, __dummy__materialized_viewnodetemplate_a10_0.INSTANCETYPE AS COL5, __dummy__materialized_viewnodetemplate_a10_0.BASEIMAGE AS COL6, __dummy__materialized_viewnodetemplate_a10_0.PACKAGES AS COL7, __dummy__materialized_viewnodetemplate_a10_0.CID AS COL8, __dummy__materialized_viewnodetemplate_a10_0.CIDTYPE AS COL9 
FROM public.__dummy__materialized_viewnodetemplate AS __dummy__materialized_viewnodetemplate_a10_0 
WHERE NOT EXISTS ( SELECT * 
FROM __temp__Δ_del_viewnodetemplate AS __temp__Δ_del_viewnodetemplate_a10 
WHERE __temp__Δ_del_viewnodetemplate_a10.CIDTYPE IS NOT DISTINCT FROM __dummy__materialized_viewnodetemplate_a10_0.CIDTYPE AND __temp__Δ_del_viewnodetemplate_a10.CID IS NOT DISTINCT FROM __dummy__materialized_viewnodetemplate_a10_0.CID AND __temp__Δ_del_viewnodetemplate_a10.PACKAGES IS NOT DISTINCT FROM __dummy__materialized_viewnodetemplate_a10_0.PACKAGES AND __temp__Δ_del_viewnodetemplate_a10.BASEIMAGE IS NOT DISTINCT FROM __dummy__materialized_viewnodetemplate_a10_0.BASEIMAGE AND __temp__Δ_del_viewnodetemplate_a10.INSTANCETYPE IS NOT DISTINCT FROM __dummy__materialized_viewnodetemplate_a10_0.INSTANCETYPE AND __temp__Δ_del_viewnodetemplate_a10.PROVIDER IS NOT DISTINCT FROM __dummy__materialized_viewnodetemplate_a10_0.PROVIDER AND __temp__Δ_del_viewnodetemplate_a10.TYP IS NOT DISTINCT FROM __dummy__materialized_viewnodetemplate_a10_0.TYP AND __temp__Δ_del_viewnodetemplate_a10.MININSTANCES IS NOT DISTINCT FROM __dummy__materialized_viewnodetemplate_a10_0.MININSTANCES AND __temp__Δ_del_viewnodetemplate_a10.MAXINSTANCES IS NOT DISTINCT FROM __dummy__materialized_viewnodetemplate_a10_0.MAXINSTANCES AND __temp__Δ_del_viewnodetemplate_a10.ID IS NOT DISTINCT FROM __dummy__materialized_viewnodetemplate_a10_0.ID )  UNION SELECT DISTINCT __temp__Δ_ins_viewnodetemplate_a10_0.ID AS COL0, __temp__Δ_ins_viewnodetemplate_a10_0.MAXINSTANCES AS COL1, __temp__Δ_ins_viewnodetemplate_a10_0.MININSTANCES AS COL2, __temp__Δ_ins_viewnodetemplate_a10_0.TYP AS COL3, __temp__Δ_ins_viewnodetemplate_a10_0.PROVIDER AS COL4, __temp__Δ_ins_viewnodetemplate_a10_0.INSTANCETYPE AS COL5, __temp__Δ_ins_viewnodetemplate_a10_0.BASEIMAGE AS COL6, __temp__Δ_ins_viewnodetemplate_a10_0.PACKAGES AS COL7, __temp__Δ_ins_viewnodetemplate_a10_0.CID AS COL8, __temp__Δ_ins_viewnodetemplate_a10_0.CIDTYPE AS COL9 
FROM __temp__Δ_ins_viewnodetemplate AS __temp__Δ_ins_viewnodetemplate_a10_0  ) AS viewnodetemplate_a10 
WHERE viewnodetemplate_a10.COL9 IS NOT DISTINCT FROM nodetemplate_a18_0.CIDTYPE AND viewnodetemplate_a10.COL8 IS NOT DISTINCT FROM nodetemplate_a18_0.CID AND viewnodetemplate_a10.COL7 IS NOT DISTINCT FROM nodetemplate_a18_0.PACKAGES AND viewnodetemplate_a10.COL6 IS NOT DISTINCT FROM nodetemplate_a18_0.BASEIMAGE AND viewnodetemplate_a10.COL5 IS NOT DISTINCT FROM nodetemplate_a18_0.INSTANCETYPE AND viewnodetemplate_a10.COL4 IS NOT DISTINCT FROM nodetemplate_a18_0.PROVIDER AND viewnodetemplate_a10.COL3 IS NOT DISTINCT FROM nodetemplate_a18_0.TYP AND viewnodetemplate_a10.COL2 IS NOT DISTINCT FROM nodetemplate_a18_0.MININSTANCES AND viewnodetemplate_a10.COL1 IS NOT DISTINCT FROM nodetemplate_a18_0.MAXINSTANCES AND viewnodetemplate_a10.COL0 IS NOT DISTINCT FROM nodetemplate_a18_0.ID ) ) AS Δ_del_nodetemplate_a18_0  ) AS Δ_del_nodetemplate_extra_alias;

CREATE TEMPORARY TABLE Δ_ins_nodetemplate WITH OIDS ON COMMIT DROP AS SELECT (ROW(COL0,COL1,COL2,COL3,COL4,COL5,COL6,COL7,COL8,COL9,COL10,COL11,COL12,COL13,COL14,COL15,COL16,COL17) :: public.nodetemplate).* 
            FROM (SELECT DISTINCT Δ_ins_nodetemplate_a18_0.COL0 AS COL0, Δ_ins_nodetemplate_a18_0.COL1 AS COL1, Δ_ins_nodetemplate_a18_0.COL2 AS COL2, Δ_ins_nodetemplate_a18_0.COL3 AS COL3, Δ_ins_nodetemplate_a18_0.COL4 AS COL4, Δ_ins_nodetemplate_a18_0.COL5 AS COL5, Δ_ins_nodetemplate_a18_0.COL6 AS COL6, Δ_ins_nodetemplate_a18_0.COL7 AS COL7, Δ_ins_nodetemplate_a18_0.COL8 AS COL8, Δ_ins_nodetemplate_a18_0.COL9 AS COL9, Δ_ins_nodetemplate_a18_0.COL10 AS COL10, Δ_ins_nodetemplate_a18_0.COL11 AS COL11, Δ_ins_nodetemplate_a18_0.COL12 AS COL12, Δ_ins_nodetemplate_a18_0.COL13 AS COL13, Δ_ins_nodetemplate_a18_0.COL14 AS COL14, Δ_ins_nodetemplate_a18_0.COL15 AS COL15, Δ_ins_nodetemplate_a18_0.COL16 AS COL16, Δ_ins_nodetemplate_a18_0.COL17 AS COL17 
FROM (SELECT DISTINCT nodetemplate_a18_1.ID AS COL0, viewnodetemplate_a10_0.COL1 AS COL1, viewnodetemplate_a10_0.COL2 AS COL2, viewnodetemplate_a10_0.COL3 AS COL3, viewnodetemplate_a10_0.COL4 AS COL4, viewnodetemplate_a10_0.COL5 AS COL5, viewnodetemplate_a10_0.COL6 AS COL6, viewnodetemplate_a10_0.COL7 AS COL7, nodetemplate_a18_1.RID1 AS COL8, nodetemplate_a18_1.RID1TYPE AS COL9, nodetemplate_a18_1.RID2 AS COL10, nodetemplate_a18_1.RID2TYPE AS COL11, viewnodetemplate_a10_0.COL8 AS COL12, viewnodetemplate_a10_0.COL9 AS COL13, nodetemplate_a18_1.DANAME AS COL14, nodetemplate_a18_1.XMLNS AS COL15, nodetemplate_a18_1.ARTIFACTREF AS COL16, nodetemplate_a18_1.ARTIFACTTYPE AS COL17 
FROM (SELECT DISTINCT __dummy__materialized_viewnodetemplate_a10_0.ID AS COL0, __dummy__materialized_viewnodetemplate_a10_0.MAXINSTANCES AS COL1, __dummy__materialized_viewnodetemplate_a10_0.MININSTANCES AS COL2, __dummy__materialized_viewnodetemplate_a10_0.TYP AS COL3, __dummy__materialized_viewnodetemplate_a10_0.PROVIDER AS COL4, __dummy__materialized_viewnodetemplate_a10_0.INSTANCETYPE AS COL5, __dummy__materialized_viewnodetemplate_a10_0.BASEIMAGE AS COL6, __dummy__materialized_viewnodetemplate_a10_0.PACKAGES AS COL7, __dummy__materialized_viewnodetemplate_a10_0.CID AS COL8, __dummy__materialized_viewnodetemplate_a10_0.CIDTYPE AS COL9 
FROM public.__dummy__materialized_viewnodetemplate AS __dummy__materialized_viewnodetemplate_a10_0 
WHERE NOT EXISTS ( SELECT * 
FROM __temp__Δ_del_viewnodetemplate AS __temp__Δ_del_viewnodetemplate_a10 
WHERE __temp__Δ_del_viewnodetemplate_a10.CIDTYPE IS NOT DISTINCT FROM __dummy__materialized_viewnodetemplate_a10_0.CIDTYPE AND __temp__Δ_del_viewnodetemplate_a10.CID IS NOT DISTINCT FROM __dummy__materialized_viewnodetemplate_a10_0.CID AND __temp__Δ_del_viewnodetemplate_a10.PACKAGES IS NOT DISTINCT FROM __dummy__materialized_viewnodetemplate_a10_0.PACKAGES AND __temp__Δ_del_viewnodetemplate_a10.BASEIMAGE IS NOT DISTINCT FROM __dummy__materialized_viewnodetemplate_a10_0.BASEIMAGE AND __temp__Δ_del_viewnodetemplate_a10.INSTANCETYPE IS NOT DISTINCT FROM __dummy__materialized_viewnodetemplate_a10_0.INSTANCETYPE AND __temp__Δ_del_viewnodetemplate_a10.PROVIDER IS NOT DISTINCT FROM __dummy__materialized_viewnodetemplate_a10_0.PROVIDER AND __temp__Δ_del_viewnodetemplate_a10.TYP IS NOT DISTINCT FROM __dummy__materialized_viewnodetemplate_a10_0.TYP AND __temp__Δ_del_viewnodetemplate_a10.MININSTANCES IS NOT DISTINCT FROM __dummy__materialized_viewnodetemplate_a10_0.MININSTANCES AND __temp__Δ_del_viewnodetemplate_a10.MAXINSTANCES IS NOT DISTINCT FROM __dummy__materialized_viewnodetemplate_a10_0.MAXINSTANCES AND __temp__Δ_del_viewnodetemplate_a10.ID IS NOT DISTINCT FROM __dummy__materialized_viewnodetemplate_a10_0.ID )  UNION SELECT DISTINCT __temp__Δ_ins_viewnodetemplate_a10_0.ID AS COL0, __temp__Δ_ins_viewnodetemplate_a10_0.MAXINSTANCES AS COL1, __temp__Δ_ins_viewnodetemplate_a10_0.MININSTANCES AS COL2, __temp__Δ_ins_viewnodetemplate_a10_0.TYP AS COL3, __temp__Δ_ins_viewnodetemplate_a10_0.PROVIDER AS COL4, __temp__Δ_ins_viewnodetemplate_a10_0.INSTANCETYPE AS COL5, __temp__Δ_ins_viewnodetemplate_a10_0.BASEIMAGE AS COL6, __temp__Δ_ins_viewnodetemplate_a10_0.PACKAGES AS COL7, __temp__Δ_ins_viewnodetemplate_a10_0.CID AS COL8, __temp__Δ_ins_viewnodetemplate_a10_0.CIDTYPE AS COL9 
FROM __temp__Δ_ins_viewnodetemplate AS __temp__Δ_ins_viewnodetemplate_a10_0  ) AS viewnodetemplate_a10_0, public.nodetemplate AS nodetemplate_a18_1 
WHERE nodetemplate_a18_1.ID = viewnodetemplate_a10_0.COL0 AND viewnodetemplate_a10_0.COL3 = 'os' AND NOT EXISTS ( SELECT * 
FROM public.nodetemplate AS nodetemplate_a18 
WHERE nodetemplate_a18.CIDTYPE IS NOT DISTINCT FROM viewnodetemplate_a10_0.COL9 AND nodetemplate_a18.CID IS NOT DISTINCT FROM viewnodetemplate_a10_0.COL8 AND nodetemplate_a18.PACKAGES IS NOT DISTINCT FROM viewnodetemplate_a10_0.COL7 AND nodetemplate_a18.BASEIMAGE IS NOT DISTINCT FROM viewnodetemplate_a10_0.COL6 AND nodetemplate_a18.INSTANCETYPE IS NOT DISTINCT FROM viewnodetemplate_a10_0.COL5 AND nodetemplate_a18.PROVIDER IS NOT DISTINCT FROM viewnodetemplate_a10_0.COL4 AND nodetemplate_a18.TYP IS NOT DISTINCT FROM viewnodetemplate_a10_0.COL3 AND nodetemplate_a18.MININSTANCES IS NOT DISTINCT FROM viewnodetemplate_a10_0.COL2 AND nodetemplate_a18.MAXINSTANCES IS NOT DISTINCT FROM viewnodetemplate_a10_0.COL1 AND nodetemplate_a18.ID IS NOT DISTINCT FROM nodetemplate_a18_1.ID ) ) AS Δ_ins_nodetemplate_a18_0  ) AS Δ_ins_nodetemplate_extra_alias; 

FOR temprecΔ_del_nodetemplate IN ( SELECT * FROM Δ_del_nodetemplate) LOOP 
            DELETE FROM public.nodetemplate WHERE ROW(ID,MAXINSTANCES,MININSTANCES,TYP,PROVIDER,INSTANCETYPE,BASEIMAGE,PACKAGES,RID1,RID1TYPE,RID2,RID2TYPE,CID,CIDTYPE,DANAME,XMLNS,ARTIFACTREF,ARTIFACTTYPE) IS NOT DISTINCT FROM  temprecΔ_del_nodetemplate;
            END LOOP;
DROP TABLE Δ_del_nodetemplate;

INSERT INTO public.nodetemplate SELECT * FROM  Δ_ins_nodetemplate; 
DROP TABLE Δ_ins_nodetemplate;

        insertion_data := (SELECT (array_to_json(array_agg(t)))::text FROM (SELECT * FROM __temp__Δ_ins_viewnodetemplate EXCEPT SELECT * FROM public.__dummy__materialized_viewnodetemplate) as t);
        IF insertion_data IS NOT DISTINCT FROM NULL THEN 
            insertion_data := '[]';
        END IF; 
        deletion_data := (SELECT (array_to_json(array_agg(t)))::text FROM (SELECT * FROM __temp__Δ_del_viewnodetemplate INTERSECT SELECT * FROM public.__dummy__materialized_viewnodetemplate) as t);
        IF deletion_data IS NOT DISTINCT FROM NULL THEN 
            deletion_data := '[]';
        END IF; 
        IF (insertion_data IS DISTINCT FROM '[]') OR (insertion_data IS DISTINCT FROM '[]') THEN 
            user_name := (SELECT session_user);
            IF NOT (user_name = 'dejima') THEN 
                json_data := concat('{"view": ' , '"public.viewnodetemplate"', ', ' , '"insertions": ' , insertion_data , ', ' , '"deletions": ' , deletion_data , '}');
                result := public.viewnodetemplate_run_shell(json_data);
                IF result = 'true' THEN 
                    REFRESH MATERIALIZED VIEW public.__dummy__materialized_viewnodetemplate;
                ELSE
                    -- RAISE LOG 'result from running the sh script: %', result;
                    RAISE check_violation USING MESSAGE = 'update on view is rejected by the external tool, result from running the sh script: ' 
                    || result;
                END IF;
            ELSE 
                RAISE LOG 'function of detecting dejima update is called by % , no request sent to dejima proxy', user_name;
            END IF;
        END IF;
    END IF;
    RETURN NULL;
  EXCEPTION
    WHEN object_not_in_prerequisite_state THEN
        RAISE object_not_in_prerequisite_state USING MESSAGE = 'no permission to insert or delete or update to source relations of public.viewnodetemplate';
    WHEN OTHERS THEN
        GET STACKED DIAGNOSTICS text_var1 = RETURNED_SQLSTATE,
                                text_var2 = PG_EXCEPTION_DETAIL,
                                text_var3 = MESSAGE_TEXT;
        RAISE SQLSTATE 'DA000' USING MESSAGE = 'error on the trigger of public.viewnodetemplate ; error code: ' || text_var1 || ' ; ' || text_var2 ||' ; ' || text_var3;
        RETURN NULL;
  END;
$$;

CREATE OR REPLACE FUNCTION public.viewnodetemplate_materialization()
RETURNS TRIGGER
LANGUAGE plpgsql
SECURITY DEFINER
AS $$
  DECLARE
  text_var1 text;
  text_var2 text;
  text_var3 text;
  BEGIN
    IF NOT EXISTS (SELECT * FROM information_schema.tables WHERE table_name = '__temp__Δ_ins_viewnodetemplate' OR table_name = '__temp__Δ_del_viewnodetemplate')
    THEN
        -- RAISE LOG 'execute procedure viewnodetemplate_materialization';
        REFRESH MATERIALIZED VIEW public.__dummy__materialized_viewnodetemplate;
        CREATE TEMPORARY TABLE __temp__Δ_ins_viewnodetemplate ( LIKE public.__dummy__materialized_viewnodetemplate INCLUDING ALL ) WITH OIDS ON COMMIT DROP;
        CREATE CONSTRAINT TRIGGER __temp__viewnodetemplate_trigger_delta_action
        AFTER INSERT OR UPDATE OR DELETE ON 
            __temp__Δ_ins_viewnodetemplate DEFERRABLE INITIALLY DEFERRED 
            FOR EACH ROW EXECUTE PROCEDURE public.viewnodetemplate_delta_action();

        CREATE TEMPORARY TABLE __temp__Δ_del_viewnodetemplate ( LIKE public.__dummy__materialized_viewnodetemplate INCLUDING ALL ) WITH OIDS ON COMMIT DROP;
        CREATE CONSTRAINT TRIGGER __temp__viewnodetemplate_trigger_delta_action
        AFTER INSERT OR UPDATE OR DELETE ON 
            __temp__Δ_del_viewnodetemplate DEFERRABLE INITIALLY DEFERRED 
            FOR EACH ROW EXECUTE PROCEDURE public.viewnodetemplate_delta_action();
    END IF;
    RETURN NULL;
  EXCEPTION
    WHEN object_not_in_prerequisite_state THEN
        RAISE object_not_in_prerequisite_state USING MESSAGE = 'no permission to insert or delete or update to source relations of public.viewnodetemplate';
    WHEN OTHERS THEN
        GET STACKED DIAGNOSTICS text_var1 = RETURNED_SQLSTATE,
                                text_var2 = PG_EXCEPTION_DETAIL,
                                text_var3 = MESSAGE_TEXT;
        RAISE SQLSTATE 'DA000' USING MESSAGE = 'error on the trigger of public.viewnodetemplate ; error code: ' || text_var1 || ' ; ' || text_var2 ||' ; ' || text_var3;
        RETURN NULL;
  END;
$$;

DROP TRIGGER IF EXISTS viewnodetemplate_trigger_materialization ON public.viewnodetemplate;
CREATE TRIGGER viewnodetemplate_trigger_materialization
    BEFORE INSERT OR UPDATE OR DELETE ON
      public.viewnodetemplate FOR EACH STATEMENT EXECUTE PROCEDURE public.viewnodetemplate_materialization();

CREATE OR REPLACE FUNCTION public.viewnodetemplate_update()
RETURNS TRIGGER
LANGUAGE plpgsql
SECURITY DEFINER
AS $$
  DECLARE
  text_var1 text;
  text_var2 text;
  text_var3 text;
  BEGIN
    -- RAISE LOG 'execute procedure viewnodetemplate_update';
    IF TG_OP = 'INSERT' THEN
      -- RAISE LOG 'NEW: %', NEW;
      DELETE FROM __temp__Δ_del_viewnodetemplate WHERE ROW(ID,MAXINSTANCES,MININSTANCES,TYP,PROVIDER,INSTANCETYPE,BASEIMAGE,PACKAGES,CID,CIDTYPE) IS NOT DISTINCT FROM NEW;
      INSERT INTO __temp__Δ_ins_viewnodetemplate SELECT (NEW).*; 
    ELSIF TG_OP = 'UPDATE' THEN
      DELETE FROM __temp__Δ_ins_viewnodetemplate WHERE ROW(ID,MAXINSTANCES,MININSTANCES,TYP,PROVIDER,INSTANCETYPE,BASEIMAGE,PACKAGES,CID,CIDTYPE) IS NOT DISTINCT FROM OLD;
      INSERT INTO __temp__Δ_del_viewnodetemplate SELECT (OLD).*;
      DELETE FROM __temp__Δ_del_viewnodetemplate WHERE ROW(ID,MAXINSTANCES,MININSTANCES,TYP,PROVIDER,INSTANCETYPE,BASEIMAGE,PACKAGES,CID,CIDTYPE) IS NOT DISTINCT FROM NEW;
      INSERT INTO __temp__Δ_ins_viewnodetemplate SELECT (NEW).*; 
    ELSIF TG_OP = 'DELETE' THEN
      -- RAISE LOG 'OLD: %', OLD;
      DELETE FROM __temp__Δ_ins_viewnodetemplate WHERE ROW(ID,MAXINSTANCES,MININSTANCES,TYP,PROVIDER,INSTANCETYPE,BASEIMAGE,PACKAGES,CID,CIDTYPE) IS NOT DISTINCT FROM OLD;
      INSERT INTO __temp__Δ_del_viewnodetemplate SELECT (OLD).*;
    END IF;
    RETURN NULL;
  EXCEPTION
    WHEN object_not_in_prerequisite_state THEN
        RAISE object_not_in_prerequisite_state USING MESSAGE = 'no permission to insert or delete or update to source relations of public.viewnodetemplate';
    WHEN OTHERS THEN
        GET STACKED DIAGNOSTICS text_var1 = RETURNED_SQLSTATE,
                                text_var2 = PG_EXCEPTION_DETAIL,
                                text_var3 = MESSAGE_TEXT;
        RAISE SQLSTATE 'DA000' USING MESSAGE = 'error on the trigger of public.viewnodetemplate ; error code: ' || text_var1 || ' ; ' || text_var2 ||' ; ' || text_var3;
        RETURN NULL;
  END;
$$;

DROP TRIGGER IF EXISTS viewnodetemplate_trigger_update ON public.viewnodetemplate;
CREATE TRIGGER viewnodetemplate_trigger_update
    INSTEAD OF INSERT OR UPDATE OR DELETE ON
      public.viewnodetemplate FOR EACH ROW EXECUTE PROCEDURE public.viewnodetemplate_update();

