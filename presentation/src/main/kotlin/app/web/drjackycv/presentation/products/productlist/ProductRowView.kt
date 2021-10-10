package app.web.drjackycv.presentation.products.productlist

//import androidx.compose.ui.res.animatedVectorResource
import android.graphics.drawable.BitmapDrawable
import androidx.compose.animation.graphics.ExperimentalAnimationGraphicsApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.palette.graphics.Palette
import app.web.drjackycv.presentation.R
import app.web.drjackycv.presentation.main.MainActivity
import app.web.drjackycv.presentation.products.entity.BeerUI
import coil.ImageLoader
import coil.annotation.ExperimentalCoilApi
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import coil.request.SuccessResult

@ExperimentalAnimationGraphicsApi
@ExperimentalCoilApi
@ExperimentalComposeUiApi
@Composable
fun ProductRowView(
    product: BeerUI,
    onSelectedProduct: (productId: Int) -> Unit
) {

    val defaultColor = MaterialTheme.colors.surface
    val cardColor = remember { mutableStateOf(defaultColor) }
    val isDark = remember { mutableStateOf(MainActivity.ThemeState.darkModeState.value) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = { onSelectedProduct(product.id) })
            .padding(4.dp),
        shape = RoundedCornerShape(4.dp),
        backgroundColor = cardColor.value,
        elevation = 2.dp
    ) {
        Row {
            Surface(
                modifier = Modifier
                    .size(72.dp)
                    .padding(4.dp),
                shape = CircleShape,
                color = MaterialTheme.colors.onSurface.copy(alpha = 0.2f)
            ) {
                val imagePainter = rememberImagePainter(
                    data = product.imageUrl,
                    builder = {
                        crossfade(true)
                        placeholder(R.drawable.ic_cloud_download)
                        allowHardware(false)
                    }
                )

                Image(
                    painter = imagePainter,
                    modifier = Modifier.size(72.dp),
                    contentDescription = product.name,
                )

                when (imagePainter.state) {
                    is ImagePainter.State.Success -> {
                        ChangeCardColor(
                            imagePainter = imagePainter,
                            cardColor = cardColor,
                            isDark = isDark
                        )
                    }
                    is ImagePainter.State.Loading -> {
                        CircularProgressIndicator(modifier = Modifier.padding(8.dp))
                    }
                    is ImagePainter.State.Error -> {
//                        val failedImage = animatedVectorResource(id = R.drawable.ic_cloud_download)
//                        var atEnd by remember { mutableStateOf(false) }

                        Image(
//                            painter = failedImage.painterFor(atEnd = atEnd),
                            painter = painterResource(id = R.drawable.ic_cloud_download),
                            modifier = Modifier
                                .size(72.dp)
                                .clickable {
//                                    atEnd = atEnd.not()
                                },
                            contentDescription = product.name,
                        )
                    }
                    ImagePainter.State.Empty -> {
                    }
                }
            }

            Column(modifier = Modifier.padding(4.dp)) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = product.name,
                    style = MaterialTheme.typography.h6,
                    fontWeight = FontWeight.Normal
                )
                CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                    Text(
                        text = product.id.toString(),
                        style = MaterialTheme.typography.body2
                    )
                }
            }
        }
    }
    Spacer(modifier = Modifier.height(2.dp))
}

@Composable
private fun ChangeCardColor(
    imagePainter: ImagePainter,
    cardColor: MutableState<Color>,
    isDark: MutableState<Boolean>
) {
    val context = LocalContext.current
    val imageLoader = ImageLoader(context)

    LaunchedEffect(key1 = imagePainter) {
        val result =
            (imageLoader.execute(imagePainter.request) as SuccessResult).drawable
        val bitmap = (result as BitmapDrawable).bitmap

        if (isDark.value) {
            val vibrant = Palette.from(bitmap)
                .generate()
                //.vibrantSwatch
                .darkVibrantSwatch

            vibrant?.let {
                cardColor.value = Color(it.rgb)
            }
        } else {
            val vibrant = Palette.from(bitmap)
                .generate()
                //.vibrantSwatch
                .lightVibrantSwatch

            vibrant?.let {
                cardColor.value = Color(it.rgb)
            }
        }
    }
}

@ExperimentalAnimationGraphicsApi
@ExperimentalCoilApi
@Preview
@Composable
fun ProductRowViewPreview() {
    ProductRowView(
        product = BeerUI(
            id = 1,
            name = "name",
            tagline = "tagline",
            description = "description",
            imageUrl = "https://images.punkapi.com/v2/5.png",
            abv = 4.9
        )
    ) { (1) }
}