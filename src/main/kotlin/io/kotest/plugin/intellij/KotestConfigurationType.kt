package io.kotest.plugin.intellij

import com.intellij.execution.configurations.ConfigurationFactory
import com.intellij.execution.configurations.ConfigurationType
import com.intellij.openapi.util.IconLoader
import javax.swing.Icon

object Icons {
   val Kotest16 = IconLoader.getIcon("/icon16.png")
   val Kotest16Grey = IconLoader.getIcon("/icon16_greyscale.png")
}

object KotestConfigurationType : ConfigurationType {

   private val factory = KotestConfigurationFactory(this)

   override fun getIcon(): Icon = Icons.Kotest16

   override fun getConfigurationTypeDescription(): String = "Run tests with Kotest"

   override fun getId(): String = Constants.FrameworkId

   override fun getDisplayName(): String = Constants.FrameworkName

   override fun getConfigurationFactories(): Array<ConfigurationFactory> = arrayOf(factory)
}
