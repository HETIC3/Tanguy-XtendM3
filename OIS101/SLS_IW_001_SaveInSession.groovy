/****************************************************************************************
 Extension Name: SLS_IW_001_SaveInSession
 Type: Interactive
 Script Author: HETIC3-DDECOSTERD
 Date: 2025-12-03
 Description:
 * add data in session to be able to know in PEUPD if the fiels has been modifed by user. 
 Revision History:
 Name                    Date             Version          Description of Changes
 DDECOSTERD         		2025-12-03     	 1.0              First version
 ******************************************************************************************/
public class SLS_IW_001_SaveInSession extends ExtendM3Trigger {
	private final InteractiveAPI interactive
	private final SessionAPI session

	/*
	 * Trigger OIS101_PEINZ_After
	 * @param interactive - Infor Interactive Interface
	 * @param session - Infor Session Interface
	 */
	public SLS_IW_001_SaveInSession(InteractiveAPI interactive, SessionAPI session) {
		this.interactive = interactive
		this.session = session

	}

	public void main() {
		session.parameters.put("SAPR", interactive.display.fields.WBSAPR)
		session.parameters.put("SACD", interactive.display.fields.WBSACD)
		session.parameters.put("UCOS", interactive.display.fields.WBUCOS)
		session.parameters.put("COCD", interactive.display.fields.WBCOCD)
		session.parameters.put("DIP1", interactive.display.fields.WBDIP1)
		session.parameters.put("DIP2", interactive.display.fields.WBDIP2)
		session.parameters.put("DIP3", interactive.display.fields.WBDIP3)
		session.parameters.put("DIP4", interactive.display.fields.WBDIP4)
		session.parameters.put("DIP5", interactive.display.fields.WBDIP5)
		session.parameters.put("DIP6", interactive.display.fields.WBDIP6)
	}
}