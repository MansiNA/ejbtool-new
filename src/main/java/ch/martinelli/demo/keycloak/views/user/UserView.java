package ch.martinelli.demo.keycloak.views.user;

import ch.martinelli.demo.keycloak.views.MainLayout;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;


@PageTitle("Info")
@Route(value = "user", layout = MainLayout.class)
@RolesAllowed("USER")
public class UserView extends HorizontalLayout {

    public UserView() {
        setPadding(true);

      //  add(new H1("User"));

        String yourContent_loggedin ="Auf dieser Seite werden Tools und Hilfsmittel bereitgestellt, um FVM-Tätigkeiten zu vereinfachen.<br />" +
                "Ebenso wird den Justizen ermöglicht, Informationen direkt aus der <b>eKP</b> bzw. <b>EGVP-E</b> zu entnehmen.<br />" +
                "Die sich aktuell noch in Planung befindlichen Funktionen sind mit <i>geplant</i> gekennzeichnet.<br />" +
                "Ideen, Anregungen oder Verbesserungsvorschläge sind herzlich willkommen!&#128512;<br /><br />" +
                "Bitte im linken Auswahlmenü die gewünschte Funktionalität auswählen.<br /><br />" +
                "Viele Grüße<br /><b>Euer Dataport FVM-Team</b>" ;


        Html html_Loggedin = new Html("<text>" + yourContent_loggedin + "</text>");

        add(html_Loggedin);
    }

}
