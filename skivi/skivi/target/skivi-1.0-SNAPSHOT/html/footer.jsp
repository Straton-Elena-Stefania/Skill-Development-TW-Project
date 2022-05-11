<%--
  Created by IntelliJ IDEA.
  User: Teddy
  Date: 5/9/2021
  Time: 6:51 PM
  To change this template use File | Settings | File Templates.
--%>
<footer>
    <section class="footerLeft">
        <img src="${pageContext.request.contextPath}/resources/images/skivi.png" alt="White logo of SkiVI">
        <h3>About<span> SkiVi</span></h3>

        <p class="footerLinks">
            <a href="#">Home</a>|
            <a href="#">About</a>|
            <a href="#">Contact</a>
        </p>

        <p class="footerProjectName">© 2021 SkiVi</p>
    </section>

    <section class="footerCenter">
        <h3>
            Contact data
        </h3>
        <div>
            <i class="fa fa-phone"></i>
            <p>0752128223</p>
        </div>
        <div>
            <i class="fa fa-inbox"></i>
            <p><a href="#">elena.straton@info.uaic.ro</a></p>
        </div>
    </section>
    <section class="footerRight">
        <h3>About the project</h3>
        <p class="footerProjectAbout">
            To design a modular Web application with the role of virtual instructor to offer sets of activities and training
            resources aimed at learning / deepening skills such as wire dancing, dead languages (eg, Latin, Aramaic),
            self-defense styles, singing to a musical instrument (harpsichord, Teremin, tuba), survival (in the jungle, in
            the home, cosmic vehicle, polluted neighborhood, ...), resuscitation procedures, making origami and others.

            Each skillModel will be implemented by an independent Web microservice that will be updated automatically or upon
            request. At least 3 such microservices will be developed as case studies.

            The basic functionalities of the system (instruction management, users interested in learning skills, etc.) will
            be offered via an API adopting the REST or GraphQL paradigm.
        </p>

    </section>
</footer>
