package io.snappydata

import java.util.Properties

import com.pivotal.gemfirexd.{FabricService, FabricLocator, TestUtil}
import com.pivotal.gemfirexd.internal.engine.distributed.utils.GemFireXDUtils
import org.scalatest.BeforeAndAfterAll

/**
 * Created by hemantb.
 */
class ServerStartSuite extends SnappyFunSuite with BeforeAndAfterAll {
  var props: Properties = null

  override def beforeAll(): Unit = {
    props = TestUtil.doCommonSetup(null)
    GemFireXDUtils.IS_TEST_MODE = true
  }

  override def afterAll(): Unit = {
    GemFireXDUtils.IS_TEST_MODE = false
  }

  test("Snappy Server start") {
    val fs: Server = ServiceManager.getServerInstance

    fs.start(props)

    assert(ServiceManager.getServerInstance.status == FabricService.State.RUNNING)

    fs.stop(null)
  }

  test("Snappy Lead start") {
    val fs: Lead = ServiceManager.getLeadInstance

    fs.start(props)

    assert(ServiceManager.getLeadInstance.status == FabricService.State.RUNNING)

    fs.stop(null)
  }

  test("Snappy Locator start") {
    val fs: Locator = ServiceManager.getLocatorInstance

    fs.start(FabricLocator.LOCATOR_DEFAULT_BIND_ADDRESS, FabricLocator.LOCATOR_DEFAULT_PORT, props)

    assert(ServiceManager.getLocatorInstance.status == FabricService.State.RUNNING)

    fs.stop(null)
  }

}