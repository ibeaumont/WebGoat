package org.owasp.webgoat.plugin;

import org.apache.ecs.Element;
import org.apache.ecs.ElementContainer;
import org.apache.ecs.StringElement;
import org.apache.ecs.html.BR;
import org.apache.ecs.html.Input;
import org.owasp.webgoat.lessons.Category;
import org.owasp.webgoat.lessons.LessonAdapter;
import org.owasp.webgoat.session.ECSFactory;
import org.owasp.webgoat.session.WebSession;

import java.util.ArrayList;
import java.util.List;

/**
 * *************************************************************************************************
 *
 *
 * This file is part of WebGoat, an Open Web Application Security Project
 * utility. For details, please see http://www.owasp.org/
 *
 * Copyright (c) 2002 - 20014 Bruce Mayhew
 *
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place - Suite 330, Boston, MA 02111-1307, USA.
 *
 * Getting Source ==============
 *
 * Source for this application is maintained at https://github.com/WebGoat/WebGoat, a repository
 * for free software projects.
 *
 * For details, please see http://webgoat.github.io
 *
 * @author Bruce Mayhew <a href="http://code.google.com/p/webgoat">WebGoat</a>
 * @created October 28, 2003
 */
public class HttpBasics extends LessonAdapter {

    //private final static String PERSON = "person";
	private final static String PERSON = "froga";
    private int requestsSinceLastComplete = 0;

    /**
     * Description of the Method
     *
     * @param s Description of the Parameter
     * @return Description of the Return Value
     */
    protected Element createContent(WebSession s) {
        requestsSinceLastComplete++;
        ElementContainer ec = new ElementContainer();

        StringBuffer person = null;
        try {
            ec.addElement(new BR());
            ec.addElement(new StringElement(getLabelManager().get("EnterYourName") + ": "));

            person = new StringBuffer(s.getParser().getStringParameter(PERSON, ""));
            person.reverse();

            Input input = new Input(Input.TEXT, PERSON, person.toString());
            ec.addElement(input);

            Element b = ECSFactory.makeButton(getLabelManager().get("Go!"));
            ec.addElement(b);
        } catch (Exception e) {
            s.setMessage("Error generating " + this.getClass().getName());
            e.printStackTrace();
        }

        if (!person.toString().equals("") && approvedNumberOfAttempts()) {
            makeSuccess(s);
            requestsSinceLastComplete = 0;
        }

        return (ec);
    }

    private boolean approvedNumberOfAttempts() {
        return requestsSinceLastComplete > 2;
    }

    /**
     * Gets the hints attribute of the HelloScreen object
     *
     * @return The hints value
     */
    public List<String> getHints(WebSession s) {
        List<String> hints = new ArrayList<String>();
        hints.add("Type in your name and press 'go'");
        hints.add("Turn on Show Parameters or other features");
        hints.add("Try to intercept the request with <a href='https://www.owasp.org/index.php/OWASP_Zed_Attack_Proxy_Project' title='Link to ZAP'>OWASP ZAP</a>");
        hints.add("Press the Show Lesson Plan button to view a lesson summary");
        hints.add("Press the Show Solution button to view a lesson solution");

        return hints;
    }

    /**
     * Resets the request counter when the lesson is restarted
     */
    @Override
    public void restartLesson() {
        requestsSinceLastComplete = 0;
    }

    /**
     * Gets the ranking attribute of the HelloScreen object
     *
     * @return The ranking value
     */
    private final static Integer DEFAULT_RANKING = new Integer(10);

    protected Integer getDefaultRanking() {
        return DEFAULT_RANKING;
    }

    protected Category getDefaultCategory() {
        return Category.GENERAL;
    }

    /**
     * Gets the title attribute of the HelloScreen object
     *
     * @return The title value
     */
    public String getTitle() {
        return ("Http Basics");
    }
}
