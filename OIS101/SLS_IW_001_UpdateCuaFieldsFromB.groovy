/****************************************************************************************
 Extension Name: SLS_IW_001_UpdateCuaFieldsFromB
 Type: Interactive
 Script Author: HETIC3-DDECOSTERD
 Date: 2025-11-07
 Description:
 * update field CUA1, CUA2 and CUA3 with data from the line
 Revision History:
 Name                    Date             Version          Description of Changes
 DDECOSTERD         		2025-11-07     	 1.0              First version
 ******************************************************************************************/
public class SLS_IW_001_UpdateCuaFieldsFromB extends ExtendM3Trigger {
	private final ProgramAPI program
	private final DatabaseAPI database

	/*
	 * Trigger OIS101_PBUPD_Post
	 * @param program - Infor Program Interface
	 * @param database - Infor Database Interface
	 */
	public SLS_IW_001_UpdateCuaFieldsFromB(ProgramAPI program, DatabaseAPI database) {
		this.program = program
		this.database = database
	}

	public void main() {
		TableRecordAPI oohead = program.getTableRecord("OOHEAD")
		if(oohead.OAORTP.toString().equals("C14") && (program.indicator.get(41) || program.indicator.get(42)) ) {
			TableRecordAPI ooline = program.getTableRecord("OOLINE")
			DBAction oolineRecord = database.table("OOLINE").index("00").build()
			DBContainer oolineContainer = oolineRecord.createContainer()
			oolineContainer.set("OBCONO",ooline.OBCONO)
			oolineContainer.set("OBORNO",ooline.OBORNO)
			oolineContainer.set("OBPONR",ooline.OBPONR)
			oolineContainer.set("OBPOSX",ooline.OBPOSX)

			oolineRecord.readLock(oolineContainer,{ LockedResult result ->
				result.setString("OBUCA1", ooline.OBPRMO.toString())
				result.setString("OBUCA2", ooline.OBDIC1.toString()+ooline.OBDIC2.toString()+ooline.OBDIC3.toString()+ooline.OBDIC4.toString()+ooline.OBDIC5.toString()+ooline.OBDIC6.toString())
				result.setString("OBUCA3", ooline.OBORNO.toString())
				result.update()
			})
		}
	}
}
