package ch.martinelli.demo.keycloak.views;

import ch.martinelli.demo.keycloak.components.appnav.AppNav;
import ch.martinelli.demo.keycloak.components.appnav.AppNavItem;
import ch.martinelli.demo.keycloak.utils.OSInfoUtil;
import ch.martinelli.demo.keycloak.views.admin.AdminView;
import ch.martinelli.demo.keycloak.views.index.IndexView;
import ch.martinelli.demo.keycloak.views.user.UserView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.server.auth.AccessAnnotationChecker;
import com.vaadin.flow.theme.lumo.LumoUtility;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;

import java.util.List;
import java.util.stream.Collectors;

/**
 * The main view is a top-level placeholder for other views.
 */
public class MainLayout extends AppLayout {

    private final AccessAnnotationChecker accessChecker;
    private H2 viewTitle;
    public static boolean isAdmin;
    public static boolean isUser;
    public static List<String> userRoles;

    public MainLayout(AccessAnnotationChecker accessChecker) {
        this.accessChecker = accessChecker;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // Check if the current user has the "ROLE_ADMIN" authority
        isAdmin = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(authority -> authority.equals("ROLE_ADMIN"));

        // Check if the current user has the "ROLE_USER" authority
        isUser = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(authority -> authority.equals("ROLE_USER"));

        // Get all roles assigned to the user
        userRoles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        System.out.println("user.."+ isUser);
        System.out.println("admin.."+ isAdmin);
        setPrimarySection(Section.DRAWER);
        addDrawerContent();
        addHeaderContent();
    }

    private void addHeaderContent() {
        DrawerToggle toggle = new DrawerToggle();
        toggle.getElement().setAttribute("aria-label", "Menu toggle");

        viewTitle = new H2();
        viewTitle.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);

        Image image = new Image("images/dataport.png", "Dataport Image");
        System.out.println("Betriebssystem: " + OSInfoUtil.getOsName());

        Span sp= new Span("V1.02");
        H1 dummy = new H1();

        HorizontalLayout header= new HorizontalLayout(toggle,dummy);
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);

        header.expand(dummy);
        header.setWidthFull();
        header.addClassNames("py-0", "px-m");
        header.add(image,sp);


        // addToNavbar(true, toggle);
        addToNavbar(true, header);
    }

    private void addDrawerContent() {
        H1 appName = new H1("eKP Web-Admin");
        //appName.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);
        appName.addClassNames(LumoUtility.FontSize.LARGE,LumoUtility.Margin.LARGE);


        Header header = new Header(appName);

        Scroller scroller = new Scroller(createNavigation());

        addToDrawer(header, scroller, createFooter());
    }

    private AppNav createNavigation() {
        // AppNav is not yet an official component.
        // For documentation, visit https://github.com/vaadin/vcf-nav#readme
        AppNav nav = new AppNav();

//        if (accessChecker.hasAccess(IndexView.class)) {
//            nav.addItem(new AppNavItem("Index", IndexView.class, "la la-globe"));
//        }
//        if (accessChecker.hasAccess(UserView.class)) {
//            nav.addItem(new AppNavItem("User", UserView.class, "la la-file"));
//        }
//        if (accessChecker.hasAccess(AdminView.class)) {
//            nav.addItem(new AppNavItem("Admin", AdminView.class, "la la-users"));
//        }
        if (accessChecker.hasAccess(CockpitView.class)) {
            nav.addItem(new AppNavItem("eKP-Cockpit", CockpitView.class, "la la-users"));
        }
        if (accessChecker.hasAccess(MailboxConfigView.class)) {
            nav.addItem(new AppNavItem("Mailbox Verwaltung", MailboxConfigView.class, "la la-users"));
        }
        if (accessChecker.hasAccess(MessageExportView.class)) {
            nav.addItem(new AppNavItem("MessageExport", MessageExportView.class, "la la-users"));
        }

        if (accessChecker.hasAccess(ConfigurationView.class)) {
            nav.addItem(new AppNavItem("Configuration", ConfigurationView.class, "la la-users"));
        }

        if (accessChecker.hasAccess(TableView.class)) {
            nav.addItem(new AppNavItem("Tableview", TableView.class, "la la-users"));
        }

        return nav;
    }

    private Footer createFooter() {
        Footer layout = new Footer();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof OAuth2AuthenticationToken token
                && token.getPrincipal() instanceof DefaultOidcUser oidcUser) {
            Avatar avatar = new Avatar(authentication.getName());
            avatar.setThemeName("xsmall");
            avatar.getElement().setAttribute("tabindex", "-1");

            MenuBar userMenu = new MenuBar();
            userMenu.setThemeName("tertiary-inline contrast");

            MenuItem userName = userMenu.addItem("");
            Div div = new Div();
            div.add(avatar);
            div.add(oidcUser.getFullName());
            div.add(new Icon("lumo", "dropdown"));
            div.getElement().getStyle().set("display", "flex");
            div.getElement().getStyle().set("align-items", "center");
            div.getElement().getStyle().set("gap", "var(--lumo-space-s)");
            userName.add(div);
            userName.getSubMenu().addItem("Sign out", e -> UI.getCurrent().getPage().setLocation("/logout"));

            layout.add(userMenu);
        } else {
            Button login = new Button("Login",
                    event -> UI.getCurrent().getPage().setLocation("/user"));
            layout.add(login);
        }

        return layout;
    }

    @Override
    protected void afterNavigation() {
        super.afterNavigation();
        viewTitle.setText(getCurrentPageTitle());
    }

    private String getCurrentPageTitle() {
        PageTitle title = getContent().getClass().getAnnotation(PageTitle.class);
        return title == null ? "" : title.value();
    }
}
