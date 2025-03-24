/**
 * ===============================================================
 * Kotlin GUI Timer Demo
 * ===============================================================
 *
 * This is a demo of how to create and use a timer in a Kotlin /
 * Swing application.
 *
 * Timers can be used to trigger events on a regular basis, after
 * a certain time has elapsed, etc.
 *
 * Timers can be started / stopped in your code, and can be
 * checked to see if they are running or not.
 */

import com.formdev.flatlaf.FlatDarkLaf
import java.awt.*
import java.awt.event.*
import javax.swing.*


/**
 * Launch the application
 */
fun main() {
    FlatDarkLaf.setup()     // Flat, dark look-and-feel
    val app = App()         // Create the app model
    MainWindow(app)         // Create and show the UI, using the app model
}


/**
 * The application class (model)
 * This is the place where any application data should be
 * stored, plus any application logic functions
 */
class App() {
    // Data fields
    var message = "TICK"

    // Application logic functions
    fun updateMessage() {
        message = if (message == "TICK") "TOCK" else "TICK"
    }
}


/**
 * Main UI window (view)
 * Defines the UI and responds to events
 * The app model should be passwd as an argument
 */
class MainWindow(val app: App) : JFrame(), ActionListener {

    // Fields to hold the UI elements
    private lateinit var infoLabel: JLabel
    private lateinit var timerButton: JButton

    // Timer
    private lateinit var demoTimer: Timer

    /**
     * Configure the UI and display it
     */
    init {
        configureWindow()               // Configure the window
        addControls()                   // Build the UI

        setLocationRelativeTo(null)     // Centre the window
        isVisible = true                // Make it visible

        demoTimer.start()               // For this demo, let's start the timer immediately

        updateView()                    // Initialise view with model data
    }

    /**
     * Configure the main window
     */
    private fun configureWindow() {
        title = "Kotlin Swing GUI Demo"
        contentPane.preferredSize = Dimension(250, 175)
        defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
        isResizable = false
        layout = null

        pack()
    }

    /**
     * Populate the UI with UI controls
     */
    private fun addControls() {
        val baseFont = Font(Font.SANS_SERIF, Font.PLAIN, 24)

        // Timer will trigger every 500ms (0.5s, or 2 times a second)
        // and the event will be handled by the main window
        demoTimer = Timer(500, this)

        infoLabel = JLabel("INFO")
        infoLabel.horizontalAlignment = SwingConstants.CENTER
        infoLabel.bounds = Rectangle(25, 25, 200, 50)
        infoLabel.font = baseFont
        add(infoLabel)

        timerButton = JButton("PAUSE / RESUME")
        timerButton.bounds = Rectangle(25, 100, 200, 50)
        timerButton.font = baseFont
        timerButton.addActionListener(this)     // Handle any clicks
        add(timerButton)
    }


    /**
     * Update the UI controls based on the current state
     * of the application model
     */
    fun updateView() {
        infoLabel.text = app.message

        timerButton.text = if (demoTimer.isRunning) "Pause" else "Resume"
    }

    /**
     * Handle any UI events (e.g. button clicks)
     * Usually this involves updating the application model
     * then refreshing the UI view
     */
    override fun actionPerformed(e: ActionEvent?) {
        when (e?.source) {
            timerButton -> {
                // Toggle the timer run/stop
                if (demoTimer.isRunning) demoTimer.stop()
                else demoTimer.start()
                // Update the view to match
                updateView()
            }

            demoTimer -> {
                println("Timer went off!")
                // Ask the app to update its status
                app.updateMessage()
                // Show the updated state
                updateView()
            }
        }
    }
}

