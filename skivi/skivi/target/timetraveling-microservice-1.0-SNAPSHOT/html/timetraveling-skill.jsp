<%--
  Created by IntelliJ IDEA.
  User: Teddy
  Date: 5/4/2021
  Time: 3:08 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Home - Skills</title>
    <link rel="stylesheet" href="../css/header.css">
    <link rel="stylesheet" href="../css/timeTraveling.css">
    <link rel="stylesheet" href="../css/modal.css">
    <link rel="stylesheet" href="../css/footer.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
</head>

<body>

<jsp:include page="header.jsp" />

<header>
    <div>
        <a class="headerLogo" href="../skills.html">
            <img src="../resources/images/skividark.png" alt="SkiVI Logo">
        </a>
    </div>
    <div class="authButtons">
        <button type="button" class="homeButton"><img class="bowOverlay" src="../resources/images/bow.png"
                                                      alt="bow overlay">Home</button>
        <div class="userNav">
            <div class="profilePictureIcon" onclick="menuToggle();">
                <img src="../resources/images/cv.jpg" alt="Profile Picture of ${userName}">
            </div>
            <div class="menuUser">
                <a class="userName" href="../usermanagement/profile.html"><strong>${userName}</strong></a>
                <ul>
                    <li>
                        <a class="tabButton" href="../../../usermanagement/profile.html"><i class="fa fa-user">My profile</i></a>
                    </li>
                    <li>
                        <a class="tabButton" href="../../../usermanagement/pageSettings.html"><i class="fa fa-cog">Settings</i></a>
                    </li>
                    <li>
                        <a class="tabButton" href="#"><i class="fa fa-sign-out">Logout</i></a>
                    </li>
                </ul>
            </div>
        </div>

    </div>
</header>

<main>
    <div id="skillSubsectionNavBar">
        <div class="skillSubsectionNavBarLinkWrapper">
            <a href="./20shair.html">Hairstyling</a>
        </div>

        <div class="skillSubsectionNavBarLinkWrapper">
            <a href="">Makeup</a>
        </div>

        <div class="skillSubsectionNavBarLinkWrapper">
            <a href="./20sclothing.html">Clothing</a>
        </div>

    </div>
    <aside>
        <ul>

            <li>
                <div class="skillSubsectionLinkWrapper">
                    <a href="./20shair.html">Hairstyling</a>
                </div>
            </li>

            <li>
                <div class="skillSubsectionLinkWrapper">
                    <a href="./20smakeup.html">Makeup</a>
                </div>
            </li>

            <li>
                <div class="skillSubsectionLinkWrapper">
                    <a href="./20sclothing.html">Clothing</a>
                </div>
            </li>

        </ul>



    </aside>
    <section id="skillsDisplay">
        <h1 class="skillChooserHeader">Time Traveling to 1920</h1>
        <c:forEach var="step" items="${stepList}">
            <div class="tutorialContentBox">
                <span class="number"><c:out value="${step.stepNumber}"/></span>
                <h3 class="stepHeader"><c:out value="${step.stepHeader}"/></h3>
                <p>
                    <c:out value="${step.stepDescription}"/>
                </p>
                <img class="stepPresentationImage"
                     src="${step.contentLink}" alt="${step.contentDescription}">
            </div>

            <c:set var="resources" value="${stepResourcesMap[step.id]}"/>
            <c:if test="${resources.size() > 0}">
                <div class="productsNeededShowcase">
                    <h3>You will need</h3>
                    <c:forEach var="resource" items="${resources}">
                        <div class="productNeeded">
                            <img src="${resource.resourceImageLink}" alt="Heat Defence Spray">
                            <button type="button"
                                    onclick="location = '${resource.resourceLink}'">Go
                                to product</button>
                            <span><c:out value="${resource.resourceDescription}"/></span>
                        </div>
                    </c:forEach>
                </div>
            </c:if>
        </c:forEach>
        <div class="tutorialContentBox">
            <div class="tutorialBoxDecoration">
                <h2 class="tutorialSubsectionTitle">
                    Clothing
                </h2>
                <img class="tutorialSakuraMotif" src="../../../resources/images/tutorialcontentborder.png" alt="sakura border">
            </div>

            <h3 class="stepHeader">How to Dress Like a 20s Flapper Girl</h3>
            <hr>
            <p>
                What did flappers wear? Free of the moral and physical constraints of
                the previous decades, flapper costumes were loose but glamorous. During
                the day flappers dressed in drop-waist dresses with a small belt or wide
                sash to accent the hip line instead of the waist. Flat chests further
                enhanced the boyish flapper outfit. The evening brought out knee-length
                and longer fringe, beading, and sequin flapper dresses with rhinestone and
                feather headbands creating a fun and feminine flapper outfit.
            </p>
            <img class="stepPresentationImage"
                 src="https://cdn.shopify.com/s/files/1/0590/2633/products/The_Kismet-A_1024x1024.jpg?v=1585421197" alt="Girls dressed in occasion evening dresses 20s dresses">
        </div>

        <div class="tutorialContentBox">
            <span class="number">1</span>
            <h3 class="stepHeader">The Dress.</h3>
            <p>
                For your authentic flapper costume, choose a dress made of a light material
                like chiffon, silk/satin, or crepe. Be sure to wear a matching slip if the
                dress is sheer. Elaborate beading will make the dress stand out but also
                expensive, so if budget is a concern, find a plain dress and jazz it up with
                sparkling accessories. Your dress should be knee-length or longer for
                authenticity (but can be shorter for a 1920s inspired look). It should fit
                loose, meaning you may need to buy a size up. It should not hug your curves.
            </p>
            <img class="stepPresentationImage"
                 src="https://vintagedancer.com/wp-content/uploads/flaper-dresses-UV-2016-350x438.jpg" alt="Girls dressed in occasion evening dresses 20s dresses">
        </div>

        <div class="productsNeededShowcase">
            <h3>You will need</h3>
            <div class="productNeeded">
                <img src="https://images-na.ssl-images-amazon.com/images/I/81jvLdzkN8L._UY879_.jpg" alt="Heat Defence Spray">
                <button>Go to product</button>
                <span>TRESemm?? Heat Defence Spray</span>
            </div>
            <div class="productNeeded">
                <img src="https://cdn.shopify.com/s/files/1/0378/5257/7932/products/B6A5412_1.jpg?v=1608474474"
                     alt="Heat Defence Spray">
                <button>Go to product</button>
                <span>VO5 Curl Defining Mousse</span>
            </div>
            <div class="productNeeded">
                <img src="https://cdn.shopify.com/s/files/1/2714/9310/products/80582_1rs.JPG?v=1571711825"
                     alt="Heat Defence Spray">
                <button>Go to product</button>
                <span>TONI&GUY Extreme Style Creation Hairspray</span>
            </div>
            <div class="productNeeded">
                <img src="https://cdn.shopify.com/s/files/1/2714/9310/products/80051_1rs.JPG?v=1571711780"
                     alt="Heat Defence Spray">
                <button>Go to product</button>
                <span>TONI&GUY Extreme Style Creation Hairspray</span>
            </div>
            <div class="productNeeded">
                <img src="https://images-na.ssl-images-amazon.com/images/I/81DfvHRs8dL._UL1500_.jpg" alt="Heat Defence Spray">
                <button>Go to product</button>
                <span>TONI&GUY Extreme Style Creation Hairspray</span>
            </div>
            <div class="productNeeded">
                <img src="https://images-na.ssl-images-amazon.com/images/I/71ksClKIJ9L._AC_UY550_.jpg"
                     alt="Heat Defence Spray">
                <button>Go to product</button>
                <span>TONI&GUY Extreme Style Creation Hairspray</span>
            </div>
        </div>

        <div class="tutorialContentBox">
            <span class="number">2</span>
            <h3 class="stepHeader">Flapper shoes.</h3>
            <p>
                The next most important part of dressing like a flapper is the shoes.
                While you can pair a plain set of black heeled pumps and be accurate to
                the decade, you will look even more smashing if you wear a pair of Mary
                Janes (one center strap) or T-strap heels. Dainty T-strap shoes in gold,
                silver or black were all the rage for eveningwear. Sometimes, black shoes
                had gold heels and crystals on the buckles.
            </p>
            <img class="stepPresentationImage"
                 src="https://i.pinimg.com/originals/4b/99/5e/4b995e4197fcca2f79d0fcd6c600405a.jpg" alt="Fancy 20s shoes with rhinestones">
        </div>

        <div class="productsNeededShowcase">
            <h3>You will need</h3>
            <div class="productNeeded">
                <img src="https://cdn.shopify.com/s/files/1/2714/9310/products/112760_3rs.jpg?v=1582329431" alt="20s tap dance shoes">
                <button type="button"
                        onclick="location = 'https://www.amazon.co.uk/dp/B002CO4MHO/ref=as_li_ss_tl?s=apparel&keywords=capezio&ie=UTF8&nodeID=679339011&sr=1-26&linkCode=gs2&linkId=64b14fd8cb4edaf8cc002309d55f01bd&tag=vintdanc-21'">Go
                    to product</button>
                <span>Capezio Women's Manhattan Xtreme Tap</span>
            </div>
            <div class="productNeeded">
                <img src="https://images-na.ssl-images-amazon.com/images/I/51ikX8M0OHL._AC_UX535_.jpg"
                     alt="20s Pink shoes">
                <button type="button"
                        onclick="location = 'https://www.amazon.com/Cambridge-Select-Womens-Round-Lavender/dp/B07P61Q2TV/ref=pd_sbs_2?pd_rd_w=QA2S7&pf_rd_p=2419a049-62bf-452e-b0d0-ca5b7e35a7b4&pf_rd_r=GH1SFSA9M6X6AB26QXS4&pd_rd_r=b362e01c-eafd-40e4-8734-65abde7a5cf3&pd_rd_wg=kDsw7&pd_rd_i=B07ZCDXF2C&psc=1'">Go
                    to product</button>
                <span>Round Toe Mid Heel Mary Jane Dress Pump </span>
            </div>
            <div class="productNeeded">
                <img src="https://images-na.ssl-images-amazon.com/images/I/514bDJdyQSL._SL1000_.jpg" alt="20s Black shoes">
                <button type="button"
                        onclick="location = 'https://www.amazon.com/Chase-Chloe-Kimmy-21-Womens-Pierced/dp/B00WWGLJDM/ref=as_li_ss_tl?s=apparel&ie=UTF8&qid=1537989362&sr=1-1&nodeID=7147440011&psd=1&keywords=Chase+&+Chloe+Kimmy-21+Women%27s+Round+Toe+Pierced+Mid+Heel+Mary+Jane+Style+Dress+Pumps&th=1&linkCode=ll1&tag=vintagedancer-20&linkId=7cae8e758e86cc519af379b9f249b501&language=en_US'">Go
                    to product</button>
                <span>Mary Jane Style Dress Pumps</span>
            </div>
        </div>

        <div class="tutorialContentBox">
            <span class="number">3</span>
            <h3 class="stepHeader">Stockings.</h3>
            <p>
                Stockings are also very important to wear with your flapper costume.
                Women did not go out in bare legs, although their stockings made them
                look like they were. Black stockings were common for day wear, but for
                evenings, nude stockings that were one shade darker than natural color
                was standard. When pastel color dresses (pink, jade green, powder blue,
                peach, violet, yellow) became common in the mid ???20s, stockings were
                made to match.
            </p>
            <img class="stepPresentationImage"
                 src="https://vintagedancer.com/wp-content/uploads/1929-stockings-heels-cuban-diamond-pointed-oaris-fancy-500x413.jpg" alt="20s picture of stockings cartoon">
        </div>

        <div class="tutorialContentBox">
            <span class="number">4</span>
            <h3 class="stepHeader">Hair accessories.</h3>
            <p>
                One of the most fun accessories a flapper outfit needs is a headband.
                Headbands that wrap around the head, forehead bands that wrap around the
                forehead, ornate hair combs, tiara crowns, and tight-fitting skull caps were
                all beautiful hair accessories. They could be a simple ribbon or much fancier
                crystal and rhinestone band or comb. Feathers were popular springing up from
                a clip or headband either to one side or smack dab in the center of the forehead.
                One very simple decoration is to wrap strands of white peals or gold beads around
                the hair. The variations are endless, so be adventurous and pick an accessory
                that you love.
            </p>
            <img class="stepPresentationImage"
                 src="https://i.pinimg.com/originals/ff/fb/8b/fffb8bac40cf197b4aa9fd846f9d66ec.jpg" alt="20s headband on model">
        </div>

        <div class="productsNeededShowcase">
            <h3>You will need</h3>
            <div class="productNeeded">
                <img src="https://images-na.ssl-images-amazon.com/images/I/71RiDPfJlRL._AC_SX425_.jpg" alt="Silver rhineshtone earrings">
                <button type="button"
                        onclick="location = 'https://www.unique-vintage.com/products/1920s-style-silver-rhinestone-nouveau-flower-drop-earrings?variant=1455406841900'">Go
                    to product</button>
                <span>TRESemm?? Heat Defence Spray</span>
            </div>
            <div class="productNeeded">
                <img src="https://images-na.ssl-images-amazon.com/images/I/91Hr9SOA4JL._AC_UX569_.jpg"
                     alt="Heat Defence Spray">
                <button type="button"
                        onclick="location = 'https://www.unique-vintage.com/products/1920s-style-silver-rhinestone-nouveau-flower-drop-earrings?variant=1455406841900'">Go
                    to product</button>
                <span>VO5 Curl Defining Mousse</span>
            </div>
            <div class="productNeeded">
                <img src="https://images-na.ssl-images-amazon.com/images/I/91RSN35OsvL._AC_UX569_.jpg" alt=" Heat Defence
            Spray">
                <button type="button"
                        onclick="location = 'https://www.unique-vintage.com/products/1920s-style-silver-rhinestone-nouveau-flower-drop-earrings?variant=1455406841900'">Go
                    to product</button>
                <span>TONI&GUY Extreme Style Creation Hairspray</span>
            </div>
        </div>

        <div class="tutorialContentBox">
            <span class="number">5</span>
            <h3 class="stepHeader">Jewelry.</h3>
            <p>
                The iconic necklace of the 1920s is the long pearl necklace. Women wore it as
                one strand, multiple strands of various lengths and even cascaded down the back
                instead of the front. A beaded tassel necklace or short Art Deco style bib
                necklace are other styles to consider.
            </p>
            <p>
                For bracelets, look for thick bangles in gold or colored plastic.
            </p>
            <p>
                Earrings were long dangling drop styles with gems in very Art Deco colors such
                as green, black, red, and crystal clear. Gem shapes were geometric square, rectangle,
                triangle, and square edge ovals. Dainty was out, BOLD was in. The long length drew
                attention to a woman???s face and ears. Studs were worn, too, although they were big
                and colorful gems ??? not little ones like we wear today.
            </p>
            <img class="stepPresentationImage"
                 src="https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fimg1.etsystatic.com%2F001%2F0%2F6457130%2Fil_fullxfull.354433891_sh18.jpg&f=1&nofb=1" alt="Fancy 20s neck decoration">
        </div>

        <div class="productsNeededShowcase">
            <h3>You will need</h3>
            <div class="productNeeded">
                <img src="https://images-na.ssl-images-amazon.com/images/I/81F-SBRW9QL._AC_UX385_.jpg"
                     alt="Heat Defence Spray">
                <button type="button"
                        onclick="location = 'https://www.amazon.co.uk/dp/B07F7SKWFQ?ie=UTF8&linkCode=gs2&creativeASIN=B07F7SKWFQ&tag=vintdanc-21&camp=1789'">Go
                    to product</button>
                <span>BABEYOND 1920s Flapper Art Deco Gatsby Earrings</span>
            </div>
            <div class="productNeeded">
                <img src="https://images-na.ssl-images-amazon.com/images/I/91XLEBlw-KL._AC_UL1500_.jpg"
                     alt="Heat Defence Spray">
                <button type="button"
                        onclick="location = 'https://www.amazon.co.uk/BABEYOND-Necklace-Earrings-Multilayer-Imitation/dp/B07Z7XRNZN/ref=pd_di_sccai_2?pd_rd_w=eKk9p&pf_rd_p=f45e6bfc-40b0-4749-aceb-1ea1cd205758&pf_rd_r=V679X3D7WJVW7JXQBWFW&pd_rd_r=26969709-4b16-4ca9-b6fd-832666542b29&pd_rd_wg=flVzC&pd_rd_i=B0792243N5&psc=1'">Go
                    to product</button>
                <span>BABEYOND 1920s Gatsby Pearl Necklace </span>
            </div>
            <div class="productNeeded">
                <img src="https://m.media-amazon.com/images/I/510cbMPUx+L._AC_SY395._SX._UX._SY._UY_.jpg"
                     alt="Heat Defence Spray">
                <button type="button"
                        onclick="location = 'https://www.amazon.co.uk/dp/B07JKDV2ST/ref=as_li_ss_tl?keywords=Lavencious%2BTennis%2BRhinestone%2BStretch%2BBracelets%2BAdjustable%2BJewelry%2BParty%2Bfor%2BWoman%2BBangle&language=en_US&ie=UTF8&sr=8-1-fkmr0&linkCode=gs2&linkId=28aabb96b939deb0abb9598d0ecb67ab&tag=vintdanc-21&th=1'">Go
                    to product</button>
                <span>Lavencious Tennis Rhinestone Stretch Bracelets </span>
            </div>
            <div class="productNeeded">
                <img src="https://m.media-amazon.com/images/I/A1HbYKXOnfL._AC_SY535._SX._UX._SY._UY_.jpg"
                     alt="Heat Defence Spray">
                <button type="button"
                        onclick="location = 'https://www.amazon.co.uk/Babeyond%C2%AE-Fashion-Flapper-Cluster-Necklace/dp/B01N5C8LO0/ref=pd_rhf_dp_p_img_3?_encoding=UTF8&psc=1&refRID=HNDYY7BQJP1DQ0WF4GA3'">Go
                    to product</button>
                <span>BABEYOND ART DECO Fashion Faux Pearls Flapper Beads </span>
            </div>
            <div class="productNeeded">
                <img src="https://images-na.ssl-images-amazon.com/images/I/61by0jFlGlL._SY500_.jpg" alt="Heat Defence Spray">
                <button type="button"
                        onclick="location = 'https://www.amazon.co.uk/Edwardian-Style-Earrings-Ivory-Pearls/dp/B073MGSJR2/ref=as_li_ss_tl?ie=UTF8&qid=1541428093&sr=8-88&keywords=1920s&linkCode=ll1&tag=vintdanc-21&linkId=ac73c62e32acc41fa15a75443bdbeac8&language=en_GB'">Go
                    to product</button>
                <span>Art Deco Edwardian Style Earrings with Ivory Pearls </span>
            </div>
            <div class="productNeeded">
                <img
                        src="https://cdn.shopify.com/s/files/1/2714/9310/products/1920s_Style_Silver_Rhinestone_Nouveau_Flower_Drop_Earrings_2048x2048.jpg?v=1571711145"
                        alt="Heat Defence Spray">
                <button type="button"
                        onclick="location = 'https://www.unique-vintage.com/products/1920s-style-silver-rhinestone-nouveau-flower-drop-earrings?variant=1455406841900'">Go
                    to product</button>
                <span>1920s Style Silver Rhinestone Nouveau Flower Drop Earrings </span>
            </div>
        </div>

        <div class="tutorialContentBox">
            <span class="number">6</span>
            <h3 class="stepHeader">Other accessories.</h3>
            <p>
                Your costume can be completed now, or you can have a little more fun and add
                some more accessories.
            <ul>
                <li>
                    A large fluffy feather fan is both sexy and a portable air conditioner for
                    hot nights.
                </li>
                <li>
                    Long back or white gloves were almost always worn to and from events but
                    removed before eating, drinking and smoking. For faux smokers, the long
                    cigarette holder is a glamorous accessory.
                </li>
                <li>
                    A small bead or mesh purse is all you need to carry a little cash and some
                    makeup.
                </li>
            </ul>

            <img class="stepPresentationImage"
                 src="https://cdn.shopify.com/s/files/1/0015/8937/9159/products/2_1bd65171-2fcd-4c9d-9afa-c204fb982885_1024x1024.jpg?v=1570029899" alt="Various 20s Accessories example">
        </div>

        <div class="finishedTutorial">
            <form>
                <button class="modalWindowOpener" type="button" data-modal="progressMadeModal"><img src="../../../resources/images/ui/donebutton.png" alt="Pink round button for finishing tutorial">
                    <span id="doneButtonText">Done</span>
                </button>
            </form>
        </div>
    </section>

    <div id="progressMadeModal" class="modalWindow">
        <div id="warningPopup">
            <form>
                <div class="warningMessage">
                    <p>Congrats!</p><p> You have made progress in this skill. Two more lessons until you have earned the <span>20s Timetraveler</span> achievement.</p>
                </div>

                <div class="modalNavButtons">
                    <button type="button" class="cancelRemovalButton modalWindowCloser" data-modal="progressMadeModal">Ok</button>
                </div>
            </form>

        </div>
    </div>


</main><footer>
    <section class="footerLeft">
        <img src="../../../resources/images/skivi.png" alt="White logo of SkiVI">
        <h3>About<span> SkiVi</span></h3>

        <p class="footerLinks">
            <a href="#">Home</a>|
            <a href="#">About</a>|
            <a href="#">Contact</a>
        </p>

        <p class="footerProjectName">?? 2021 SkiVi</p>
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

            Each skill will be implemented by an independent Web microservice that will be updated automatically or upon
            request. At least 3 such microservices will be developed as case studies.

            The basic functionalities of the system (instruction management, users interested in learning skills, etc.) will
            be offered via an API adopting the REST or GraphQL paradigm.
        </p>

    </section>
</footer>


<script src="../js/modalwindowpop.js"></script>
<script src="../js/modalwindowclose.js"></script>
<script src="../js/usermenutoggle.js"></script>

</body>

</html>