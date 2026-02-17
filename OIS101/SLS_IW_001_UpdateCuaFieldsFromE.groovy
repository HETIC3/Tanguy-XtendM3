/****************************************************************************************
 Extension Name: SLS_IW_001_UpdateCuaFieldsFromE
 Type: Interactive
 Script Author: HETIC3-DDECOSTERD
 Date: 2025-11-07
 Description:
 * update field CUA1, CUA2 and CUA3 with data from the line
 Revision History:
 Name                    Date             Version          Description of Changes
 DDECOSTERD         		2025-11-07     	 1.0              First version
 ******************************************************************************************/
class SLS_IW_001_UpdateCuaFieldsFromE  extends ExtendM3Trigger{
	private final ProgramAPI program
	private final DatabaseAPI database
	private final InteractiveAPI interactive
	private final SessionAPI session


	/*
	 * Trigger OIS101_PEUPD_Post
	 * @param program - Infor Program Interface
	 * @param database - Infor Database Interface
	 * @param interactive - Infor Interactive Interface
	 * @param session - Infor Session Interface
	 */
	public SLS_IW_001_UpdateCuaFieldsFromE(ProgramAPI program, DatabaseAPI database,InteractiveAPI interactive, SessionAPI session) {
		this.program = program
		this.database = database
		this.interactive = interactive
		this.session = session
	}

	public void main() {
		TableRecordAPI oohead = program.getTableRecord("OOHEAD")
		int updateLine = 0

		if( oohead.OAORTP.toString().equals("C14") ) {
			if( program.indicator.get(41) || program.indicator.get(42) ) {
				updateLine = 3
			}
		}else {

			if( session.parameters.get("SAPR") != interactive.display.fields.WBSAPR || session.parameters.get("SACD") != interactive.display.fields.WBSACD
					|| session.parameters.get("UCOS") != interactive.display.fields.WBUCOS || session.parameters.get("COCD") != interactive.display.fields.WBCOCD
					|| session.parameters.get("DIP1") != interactive.display.fields.WBDIP1 || session.parameters.get("DIP2") != interactive.display.fields.WBDIP2
					|| session.parameters.get("DIP3") != interactive.display.fields.WBDIP3 || session.parameters.get("DIP4") != interactive.display.fields.WBDIP4
					|| session.parameters.get("DIP5") != interactive.display.fields.WBDIP5 || session.parameters.get("DIP6") != interactive.display.fields.WBDIP6 ) {
				updateLine = 2
			}
		}

		if(updateLine > 0) {
			TableRecordAPI ooline = program.getTableRecord("OOLINE")
			DBAction oolineRecord = database.table("OOLINE").index("00").build()
			DBContainer oolineContainer = oolineRecord.createContainer()
			oolineContainer.set("OBCONO",ooline.OBCONO)
			oolineContainer.set("OBORNO",ooline.OBORNO)
			oolineContainer.set("OBPONR",ooline.OBPONR)
			oolineContainer.set("OBPOSX",ooline.OBPOSX)

			oolineRecord.readLock(oolineContainer,{ LockedResult result ->
				if(updateLine == 3) {
					result.setString("OBUCA1", ooline.OBPRMO.toString())
					result.setString("OBUCA2", ooline.OBDIC1.toString()+ooline.OBDIC2.toString()+ooline.OBDIC3.toString()+ooline.OBDIC4.toString()+ooline.OBDIC5.toString()+ooline.OBDIC6.toString())
					result.setString("OBUCA3", ooline.OBORNO.toString())
				}else {
					result.setString("OBUCA1", "")
					result.setString("OBUCA2","")
				}
				result.update()
			})

		}
	}
}